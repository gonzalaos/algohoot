import React from 'react'
import './Button.css'

interface ButtonProps {
    children: React.ReactNode
    type?: 'button' | 'submit' | 'reset'
    variant?: 'primary' | 'secondary'
    disabled?: boolean
    loading?: boolean
    onClick?: () => void
    className?: string
}

export const Button: React.FC<ButtonProps> = ({
    children,
    type = 'button',
    variant = 'primary',
    disabled = false,
    loading = false,
    onClick,
    className = ''
}) => {
    return (
        <button
            type={type}
            className={`btn btn--${variant} ${loading ? 'btn--loading' : ''} ${className}`}
            disabled={disabled || loading}
            onClick={onClick}
        >
            {loading ? '⏳ Cargando...' : children}
        </button>
    )
}