import React, {useCallback, useState, useMemo} from 'react'
import { useLocation } from 'wouter'
import { ZodError } from 'zod'

import { useGameCreate, useGameJoin } from '../services/GameServices'
import { GameCreateSchema, GameJoinSchema } from '../types/Game'

import { Button } from '../components/Button/Button'
import { Input } from '../components/Input/Input'
import { Card } from '../components/Card/Card'

import '../styles/Home.css'

const Home: React.FC = () => {
  const [isJoinMode, setIsJoinMode] = useState<boolean>(false)
  const [submitted, setSubmitted] = useState<boolean>(false)

  const [username, setUsername] = useState<string>('')
  const [gameCode, setGameCode] = useState<string>('')

  const [validationError, setValidationError] = useState<{username?: string, code?: string} | null>(null)

  const { mutate: createGame, isPending: loadingCreate, reset: resetCreate, error: createError } = useGameCreate()
  const { mutate: joinGame, isPending: loadingJoin, reset: resetJoin, error: joinError } = useGameJoin()

  const loading = loadingCreate || loadingJoin
  const backendError = createError || joinError
  const [, navigate] = useLocation();

  const toggleMode = (mode: boolean) => {
    if(loading) return
    setUsername('')
    setGameCode('')
    setSubmitted(false)
    setIsJoinMode(mode)
    setValidationError(null)
    resetCreate()
    resetJoin()
  }

  const handleSubmit = useCallback(async(event: React.FormEvent) => {
      event.preventDefault()
      if (loading) return;

      setSubmitted(true)
      setValidationError(null)
      resetCreate()
      resetJoin()

      try {
        if(isJoinMode) {
          GameJoinSchema.parse({username, gameCode})
          joinGame({username, gameCode}, {
            onSuccess: (response) => {
              console.log("Joined game:", response)
              navigate(`/lobby/${response.gameId}`)
            },
          })
        } else {
          GameCreateSchema.parse({username})
          createGame({username}, {
            onSuccess: (newGame) => {
              console.log("Game created:", newGame)
              navigate(`/lobby/${newGame.gameId}`)
            }
          })
        }
      } catch(error) {
        if(error instanceof ZodError) {
          const fieldErrors: {username?: string, code?: string} = {}
          error.issues.forEach(issue => {
            if (issue.path.includes('username')) fieldErrors.username = issue.message
            if (issue.path.includes('gameCode')) fieldErrors.code = issue.message
          })
          setValidationError(fieldErrors)
        }
      }
    },
    [username, gameCode, isJoinMode, createGame, joinGame, loading, navigate, resetCreate, resetJoin])

  const handleInputChange = (setter: React.Dispatch<React.SetStateAction<string>>) => (value: string) => {
    setter(value)
    if (backendError) {
      resetCreate()
      resetJoin()
    }
    if (validationError) setValidationError(null)
  }

  const displayErrors = useMemo(() => {
    const result = { username: '', code: '', global: '' }

    if(submitted) {
      if(!username.trim()) result.username = 'Ingresa un nombre de usuario.'
      if(isJoinMode && !gameCode.trim()) result.code = 'Ingresa el código de la partida.'
    }

    if(!result.username && validationError?.username) result.username = validationError.username
    if(!result.code && validationError?.code) result.code = validationError.code

    if(backendError) {
      const msg = backendError.message.toLowerCase()

      if(msg.includes('nombre') || msg.includes('username')) {
        if(!result.username) result.username = backendError.message
      } else if(msg.includes('código') || msg.includes('partida') || msg.includes('game')) {
        if(!result.code) result.code = backendError.message
      } else {
        result.global = backendError.message
      }
    }

    return result
  }, [username, gameCode, submitted, isJoinMode, validationError, backendError])

  return (
    <div className="home-container">
      <Card>
        <div className="home-header">
          <h1 className="home-title">🎮 ALGOHOOT</h1>
          <p className="home-subtitle">¡Bienvenido al Juego!</p>
        </div>

        <div className="mode-toggle-container">
          <button
            type="button"
            className={`toggle-btn ${!isJoinMode ? 'active create' : ''}`}
            onClick={() => toggleMode(false)}
          >
            Crear Partida
          </button>
          <button
            type="button"
            className={`toggle-btn ${isJoinMode ? 'active join' : ''}`}
            onClick={() => toggleMode(true)}
          >
            Unirse a Partida
          </button>
        </div>

        <form 
          onSubmit={handleSubmit}
          className={`home-form ${isJoinMode ? 'join-mode' : ''}`}
        >
          <div className={`input-group${isJoinMode ? '-join' : ''}`}>
            <Input
              type="text"
              placeholder="Elige tu nombre de jugador"
              value={username}
              onChange={handleInputChange(setUsername)}
              error={displayErrors.username || undefined}
              disabled={loading}
            />
          </div>

          {isJoinMode && (
            <div className="input-group slide-in-animation">
              <Input
                type="text"
                placeholder="Ingresa el código de partida"
                value={gameCode}
                onChange={handleInputChange(setGameCode)}
                error={displayErrors.code || undefined}
                disabled={loading}
              />
            </div>
          )}

          {displayErrors.global && (
            <div className="global-error-message" style={{ color: '#ff4d4f', marginBottom: '10px', fontSize: '0.9rem', textAlign: 'center' }}>
              {displayErrors.global}
            </div>
          )}

          <Button
            type="submit"
            variant={isJoinMode ? 'secondary' : 'primary'}
            disabled={loading}
            loading={loading}
            className={isJoinMode ? 'btn-join' : 'btn-create'}
          >
            {isJoinMode ? '🔗 UNIRSE A PARTIDA' : '🚀 COMENZAR A JUGAR'}
          </Button>
        </form>
      </Card>
    </div>
	)
}

export default Home