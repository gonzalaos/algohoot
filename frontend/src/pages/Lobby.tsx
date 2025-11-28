import { Button } from "../components/Button/Button"
import { Card } from "../components/Card/Card"
import '../styles/Lobby.css'

const Lobby = () => {
  return (
    <div className="lobby-container">
      <Card className="lobby-card">
        <div className="lobby-header">
          <h1 className="lobby-title">Nueva partida</h1>
        </div>
      
        <div className="lobby-code-info">
          <h1 className="lobby-code">XGHA12</h1>
          <small className="lobby-info">¡Comparte este código para que tus amigos se unan o presiona 'Comenzar juego' para iniciar el juego!</small>
        </div>
        
        <div className="lobby-players-section">
          <div className="lobby-players-info">
            <span className="lobby-players-label">Cantidad de jugadores:</span>
            <div className="lobby-players-count">
              <span className="lobby-players-number">1</span>
              <span className="lobby-players-indicator">
                <span className="lobby-players-ping"></span>
                <span className="lobby-players-dot"></span>
              </span>
            </div>
          </div>
        </div>

        <Button
          className="lobby-btn"
          type="submit"
          variant="primary"
          disabled={false}
          loading={false}  
        >
          🚀 COMENZAR JUEGO
        </Button>
      </Card>
    </div>
  )
}

export default Lobby

