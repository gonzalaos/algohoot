import { useMutation } from '@tanstack/react-query'
import { BASE_API_URL } from '../config/app-query-client'
import { type Game, GameSchema, type GameCreateRequest, GameCreateSchema, 
  type GameJoinRequest, GameJoinSchema,
} from '../types/Game'

const translateBackendError = (message: string, status: number): string => {
  const msgLower = message.toLowerCase()
  
  if (msgLower.includes("no game was found") || msgLower.includes("code not exist")) {
    return "No se encontró una partida con ese código."
  }

  if (msgLower.includes("already taken") || msgLower.includes("username")) {
    return "Este nombre ya está en uso en esta partida."
  }

  if (msgLower.includes("started") || msgLower.includes("full")) {
    return "La partida ya ha comenzado o está llena."
  }

  if (status === 500) return "Error interno del servidor."
  if (status === 400) return "Datos inválidos. Verifica la información."

  return message || "Ocurrió un error inesperado."
}

export function useGameCreate() {
  return useMutation({
    mutationFn: async (data: GameCreateRequest): Promise<Game> => {
      GameCreateSchema.parse(data)

      try {
        const response = await fetch(`${BASE_API_URL}/game`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(data),
        })

        if (!response.ok) {
          const errorData = await response.json().catch(() => ({}))
          throw new Error(translateBackendError(errorData.message || "", response.status))
        }

        const responseData = await response.json()
        return GameSchema.parse(responseData)

      } catch (error: any) {
        if (error.message.includes('Failed to fetch') || error.name === 'TypeError') {
           throw new Error('Error de conexión. Intenta nuevamente.')
        }
        throw error
      }
    }
  })
}

export function useGameJoin() {
  return useMutation({
    mutationFn: async (data: GameJoinRequest): Promise<Game> => {
      GameJoinSchema.parse(data)

      try {
        const response = await fetch(`${BASE_API_URL}/game/join`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(data),
        })

        if (!response.ok) {
          const errorData = await response.json().catch(() => ({}))
          throw new Error(translateBackendError(errorData.message || "", response.status))
        }

        const responseData = await response.json()
        return GameSchema.parse(responseData)

      } catch (error: any) {
        if (error.message.includes('Failed to fetch') || error.name === 'TypeError') {
           throw new Error('Error de conexión. Intenta nuevamente.')
        }
        throw error
      }
    }
  })
}