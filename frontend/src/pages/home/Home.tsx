import React, {useCallback, useState} from "react";
import { usePlayerCreate } from "../../services/playerServices";
import type { Player, InputEvent } from "../../types/types";
import "../../styles/home/Home.css";

const Home: React.FC = () => {
	const [username, setUsername] = useState<string>('');
	const [player, setPlayer] = useState<Player | null>(null);
  const [submitted, setSubmitted] =  useState<boolean>(false);
  const { mutate: createPlayer, error: mutationError, isPending: loading, reset } = usePlayerCreate();

  const handleSubmit = useCallback(
    async(event: React.FormEvent) => {
      event.preventDefault();
      setSubmitted(true);

      if (!username.trim() || username.length < 3 || username.length > 20) {
        return;
      }

      createPlayer(
        { username },
        {
          onSuccess: (newPlayer) => {
            setPlayer(newPlayer);
            console.log('Usuario creado:', newPlayer);
          },
        }
      );
    },
    [username, createPlayer]
  );

	const handleUsernameChange = useCallback((event: InputEvent): void => {
    const newValue = event.target.value;
    setUsername(newValue);

    if (submitted || mutationError) {
      setSubmitted(false);
      reset();
    }
  }, [submitted, mutationError, reset]);

  const error = React.useMemo(() => {
    if (!submitted) return null;
    if (!username.trim()) return 'Ingresa un nombre de usuario.';
    if (username.length < 3) return 'El username debe tener al menos 3 caracteres.';
    if (username.length > 20) return 'El username no puede tener más de 20 caracteres.';
    if (mutationError) return mutationError.message;
    return null;
  }, [username, mutationError, submitted]);

  const showSuggestions = error && error.includes('ya está en uso');

	return (
    <div className="home-container">
      <div className="home-card">

        <div className="home-header">
          <h1 className="home-title">🎮 ALGOHOOT</h1>
          <p className="home-subtitle">¡Bienvenido al Juego!</p>
        </div>

        {player && (
          <div className="player-info">
            <p>¡Hola, <strong>{player.username}</strong>!</p>
            <small>
              ID: {player.id} • 
            </small>
          </div>
        )}

        <form onSubmit={handleSubmit} className="home-form">
          <div className="input-group">
            <div className="input-wrapper">
              <input
              type="text"
              placeholder="Elige tu nombre de jugador"
              value={username}
              onChange={handleUsernameChange}
              className={`username-input ${error ? 'input-error' : ''}`}
              disabled={loading || !!player}
              />

              {error && <span className="error-icon">!</span>}
            </div>

            {error && <span className="error-message">{error}</span>}

            {showSuggestions && (
              <div className="suggestions">
                <small>Sugerencia: ¿Qué tal {username}_123?</small>
              </div>
            )}
          </div>

          <button 
            type="submit" 
            className="primary-button"
            disabled={loading || !!player}
          >
            {loading ? '⏳ Verificando...' : '🚀 COMENZAR A JUGAR'}
          </button>
        </form>

        <button 
          className="secondary-button" 
          disabled={loading || !!player}
        >
          🔗 UNIRSE A PARTIDA
        </button>

        <div className="home-footer">
          <small>
            Al ingresar tu nombre, podrás crear partidas o unirte a existentes.
          </small>
        </div>
      </div>
    </div>
	)
}

export default Home;
