import type React from "react";

export interface Player {
    id: number;
    username: string;
}

export interface PropsInput {
    value: string;
    onChange: (value: string) => void;
    placeholder?: boolean;
    error?: string | null;
    disable?: boolean;
}

export interface PropsButton {
    children: React.ReactNode;
    onClick?: () => void;
    type?: 'button' | 'submit' | 'reset';
    variant?: 'primary' | 'secundary' | 'danger';
    disable?: boolean;
    loading?: boolean;
}

export type FormEvent = React.FormEvent<HTMLFormElement>;
export type InputEvent = React.ChangeEvent<HTMLInputElement>;
export type ClickEvent = React.MouseEvent<HTMLButtonElement>;