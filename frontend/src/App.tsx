import React from 'react'
import { QueryClientProvider } from '@tanstack/react-query'
import { appQueryClient } from './config/app-query-client'
import { Route } from 'wouter'
import './App.css'
import Home from './pages/Home'
import Lobby from './pages/Lobby'

const App: React.FC = () => {
  return (
    <QueryClientProvider client={appQueryClient}>
      <div className='app'>
        <Route path="/" component={Home} />
        <Route path="/lobby" component={Lobby} />
      </div>
    </QueryClientProvider>
  )
}

export default App
