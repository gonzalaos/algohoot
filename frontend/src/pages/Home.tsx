import React, {useCallback, useState} from 'react'
import { useGameCreate } from '../services/GameServices'
import { type Game, GameCreateSchema } from '../types/Game'
import { ZodError } from 'zod'
import { Button } from '../components/Button/Button'
import { Input } from '../components/Input/Input'
import { Card } from '../components/Card/Card'
import { useLocation } from 'wouter'
import '../styles/Home.css'

const Home: React.FC = () => {
  const [username, setUsername] = useState<string>('')
  const [game, setGame] = useState<Game | null>(null)
  const [submitted, setSubmitted] = useState<boolean>(false)
  const [validationError, setValidationError] = useState<string | null>(null)

  const { mutate: createGame, error: createGameError, isPending: loadingGame, reset } = useGameCreate()

  const [, navigate] = useLocation();

  const handleSubmit = useCallback(async(event: React.FormEvent) => {
      event.preventDefault()
      if (loadingGame || game) return;

      setSubmitted(true)
      setValidationError(null)

      try {
        GameCreateSchema.parse({username})
        createGame(
          { username },
          {
            onSuccess:(newGame) => {
              console.log("Game created:", newGame);
              setGame(newGame)
              navigate('/lobby')
            }
          }
        )
      } catch(error) {
        if(error instanceof ZodError) {
          const firstError = error.issues[0]
          setValidationError(firstError.message)
        }
      }
    },
    [username, createGame, loadingGame, game, navigate]
  )

  const handleUsernameChange = useCallback((value: string): void => {
    setUsername(value)
    if (submitted || validationError || createGameError) {
      setSubmitted(false)
      setValidationError(null)
      reset()
    }
  }, [submitted, validationError, createGameError, reset])

  const error = React.useMemo(() => {
    if (!submitted) return null
    if (!username.trim()) return 'Ingresa un nombre de usuario.'
    if (validationError) return validationError
    if (createGameError) return createGameError.message
    return null
  }, [username, createGameError, validationError, submitted])

  const showSuggestions = error && error.includes('ya está en uso')

  return (
    <div className="home-container">
      <Card>
        <div className="home-header">
          <h1 className="home-title">🎮 ALGOHOOT</h1>
          <p className="home-subtitle">¡Bienvenido al Juego!</p>
        </div>

        <form onSubmit={handleSubmit} className="home-form">
          <div className="input-group">
            <Input
              type="text"
              placeholder="Elige tu nombre de jugador"
              value={username}
              onChange={handleUsernameChange}
              error={error}
              disabled={loadingGame || !!game}
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
            disabled={loadingGame || !!game}
            loading={loadingGame}  
          >
            🚀 CREAR PARTIDA
          </Button>
        </form>

        <Button 
          variant="secondary"
          disabled={loadingGame || !!game}
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