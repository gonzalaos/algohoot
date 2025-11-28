// import { useCallback } from "react";
// import type { Player } from "../../types/Player";

// export function usePlayerCreateMock() {
//   const mutate = useCallback(
//     (data: { username: string }, opts?: { onSuccess?: (p: Player) => void }) => {
//       console.log("MOCK: creating player...");

//       setTimeout(() => {
//         const fakePlayer: Player = {
//           id: Math.floor(Math.random() * 100000),
//           username: data.username,
//         };

//         console.log("MOCK: created player:", fakePlayer);

//         opts?.onSuccess?.(fakePlayer);
//       }, 600);
//     },
//     []
//   );

//   return {
//     mutate,
//     error: null,
//     isPending: false,
//     reset: () => {},
//   };
// }
