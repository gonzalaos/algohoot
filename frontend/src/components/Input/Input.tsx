import React from 'react'
import './Input.css'

interface InputProps {
  type?: string              
  placeholder?: string
  value: string
  onChange: (value: string) => void
  error?: string | null
  disabled?: boolean
  className?: string
}

export const Input: React.FC<InputProps> = ({
  type = 'text',
  placeholder,
  value,
  onChange,
  error = null,         
  disabled = false,      
  className = ''
}) => {
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    onChange(e.target.value)
  }

  return (
    <div className="input-container">
      <div className="input-wrapper">
        <input
          type={type}
          placeholder={placeholder}
          value={value}
          onChange={handleChange}
          disabled={disabled}
          className={`input ${error ? 'input--error' : ''} ${className}`}
        />
        {error && <span className="input-error-icon">!</span>}
      </div>
      {error && <span className="input-error-message">{error}</span>}
    </div>
  );
};