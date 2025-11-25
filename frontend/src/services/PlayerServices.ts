import { useMutation } from '@tanstack/react-query'
import { BASE_API_URL } from '../config/app-query-client'
import { type Player, PlayerSchema, type PlayerCreateRequest, PlayerCreateSchema } from '../types/Player'

export function usePlayerCreate() {
  return useMutation({
    mutationFn: async (data: PlayerCreateRequest): Promise<Player> => {
      const validatedData = PlayerCreateSchema.parse(data)

      const response = await fetch(`${BASE_API_URL}/player`, {
      method: "POST",
      headers: {
        "Accept": "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(validatedData),
      })

      if(!response.ok) {
        const errorData = await response.json().catch(() => ({}))

        switch (response.status) {
          case 400:
            throw new Error('Datos inválidos. Por favor verifica la información.')
          case 409:
            throw new Error('Este nombre de usuario ya está en uso. Por favor elige otro.')
          case 500:
            throw new Error('Error del servidor.')
          default:
            throw new Error(errorData.message || `Error ${response.status}`)
        }
      }

      const reponseData = await response.json()
      return PlayerSchema.parse(reponseData)
    }
  })
}