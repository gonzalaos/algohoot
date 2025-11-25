import { z } from 'zod'

export const PlayerSchema = z.object({
  id: z.number(),
  username: z.string().min(1, "username cannot be empty")
})

export const PlayerCreateSchema = z.object({
  username: z.string()
    .min(3, "El nombre de usuario debe tener al menos 3 caracteres")
    .max(20, "El nombre de usuario no puede tener más de 20 caracteres")
})

export type Player = z.infer<typeof PlayerSchema>;
export type PlayerCreateRequest = z.infer<typeof PlayerCreateSchema>;