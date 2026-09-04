package com.fieldservice.app.presentation;

/**
 * Estado genérico para telas que carregam uma lista/entidade de uma fonte assíncrona
 * (hoje o mock, futuramente a API). Mantém as Activities sem if/else espalhado.
 */
public final class UiState<T> {

    public enum Type {
        LOADING, SUCCESS, ERROR, EMPTY
    }

    private final Type type;
    private final T data;
    private final String errorMessage;

    private UiState(Type type, T data, String errorMessage) {
        this.type = type;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> UiState<T> loading() {
        return new UiState<>(Type.LOADING, null, null);
    }

    public static <T> UiState<T> success(T data) {
        return new UiState<>(Type.SUCCESS, data, null);
    }

    public static <T> UiState<T> error(String message) {
        return new UiState<>(Type.ERROR, null, message);
    }

    public static <T> UiState<T> empty() {
        return new UiState<>(Type.EMPTY, null, null);
    }

    public Type getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
