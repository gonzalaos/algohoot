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

export type Game = z.infer<typeof GameSchema>;
export type GameCreateRequest = z.infer<typeof GameCreateSchema>;