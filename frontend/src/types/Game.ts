import { z } from 'zod'

export const GameSchema = z.object({
  gameId: z.number().int(),
  hostPlayerId: z.number().int(),
})

export const GameCreateSchema = z.object({
  username: z.string()
    .min(3, "El nombre de usuario debe tener al menos 3 caracteres")
    .max(20, "El nombre de usuario no puede tener más de 20 caracteres")
})

export const GameJoinSchema = z.object({
  username: z.string()
    .min(3, "El nombre de usuario debe tener al menos 3 caracteres")
    .max(20, "El nombre de usuario no puede tener más de 20 caracteres"),
  gameCode: z.string()
    .min(6, "El código debe tener 6 carácteres")
    .max(6, "El código debe tener 6 carácteres")
})

export type Game = z.infer<typeof GameSchema>
export type GameCreateRequest = z.infer<typeof GameCreateSchema>
export type GameJoinRequest = z.infer<typeof GameJoinSchema>