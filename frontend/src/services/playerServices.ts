import { useMutation } from "@tanstack/react-query";
import { BASE_API_URL } from "../config/app-query-client";
import type { Player } from "../types/types";

async function createPlayer(data: { username: string }): Promise<Player> {
  const response = await fetch(`${BASE_API_URL}/player`, {
    method: "POST",
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if(!response.ok) {
    const errorData = await response.json().catch(() => ({
      message: "Error del servidor"
    }));

    if (response.status === 409 || 
      errorData.message?.toLowerCase().includes('already exists')) {
      throw new Error('Este nombre de usuario ya está en uso. Por favor elige otro.');
    }

    throw new Error(errorData.message || `Error ${response.status}`);
  }

  return response.json();
}

export function usePlayerCreate() {
  return useMutation({
    mutationFn: createPlayer,
  });
}
