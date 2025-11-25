import React from 'react'
import { QueryClientProvider } from '@tanstack/react-query'
import { appQueryClient } from './config/app-query-client'
import './App.css'
import Home from './pages/Home'

const App: React.FC = () => {
  return (
    <QueryClientProvider client={appQueryClient}>
      <div className='app'>
        <Home />
      </div>
    </QueryClientProvider>
  )
}

export default App
