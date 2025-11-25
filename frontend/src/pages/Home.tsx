import React, {useCallback, useState} from 'react'
import { usePlayerCreate } from '../services/PlayerServices'
import { type Player, PlayerCreateSchema } from '../types/Player'
import { ZodError } from 'zod'
import { Button } from '../components/Button/Button'
import { Input } from '../components/Input/Input'
import { Card } from '../components/Card/Card'
import '../styles/home/Home.css'

const Home: React.FC = () => {
  const [username, setUsername] = useState<string>('')
  const [player, setPlayer] = useState<Player | null>(null)
  const [submitted, setSubmitted] = useState<boolean>(false)
  const [validationError, setValidationError] = useState<string | null>(null)

  const { mutate: createPlayer, error: mutationError, isPending: loading, reset } = usePlayerCreate()

  const handleSubmit = useCallback(async(event: React.FormEvent) => {
      event.preventDefault()
      if (loading || !!player) return;
      setSubmitted(true)
      setValidationError(null)

      try {
        PlayerCreateSchema.parse({ username })
        createPlayer(
          { username },
          {
            onSuccess: (newPlayer) => {
              setPlayer(newPlayer);
              console.log('Usuario creado:', newPlayer)
            },
          }
        );
      } catch (error) {
        if (error instanceof ZodError) {
          const firstError = error.issues[0]
          setValidationError(firstError.message)
        }
      }
    },
    [username, createPlayer, loading, player]
  )

  const handleUsernameChange = useCallback((value: string): void => {
    setUsername(value)
    if (submitted || validationError || mutationError) {
      setSubmitted(false)
      setValidationError(null)
      reset()
    }
  }, [submitted, validationError, mutationError, reset])

  const error = React.useMemo(() => {
    if (!submitted) return null
    if (!username.trim()) return 'Ingresa un nombre de usuario.'
    if (validationError) return validationError
    if (mutationError) return mutationError.message
    return null
  }, [username, mutationError, validationError, submitted])

  const showSuggestions = error && error.includes('ya está en uso')

  return (
    <div className="home-container">
      <Card>
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
            <Input
              type="text"
              placeholder="Elige tu nombre de jugador"
              value={username}
              onChange={handleUsernameChange}
              error={error}
              disabled={loading || !!player}
            />
            {showSuggestions && (
              <div className="suggestions">
                <small>Sugerencia: ¿Qué tal {username}_123?</small>
              </div>
            )}
          </div>

          <Button
            type="submit"
            variant="primary"
            disabled={loading || !!player}
            loading={loading}  
          >
            🚀 COMENZAR A JUGAR
          </Button>
        </form>

        <Button 
          variant="secondary"
          disabled={loading || !!player}
        >
          🔗 UNIRSE A PARTIDA
        </Button>

        <div className="home-footer">
          <small>
            Al ingresar tu nombre, podrás crear partidas o unirte a existentes.
          </small>
        </div>
      </Card>
    </div>
	)
}

export default Home