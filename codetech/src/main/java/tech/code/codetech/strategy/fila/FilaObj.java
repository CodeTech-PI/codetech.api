package tech.code.codetech.strategy.fila;

public class FilaObj<T> {

    private int tamanho;
    private T[] fila;

    public FilaObj(int capacidade) {
        this.tamanho = 0;
        this.fila = fila;
    }

    public boolean isEmpty(){
        return tamanho == 0;
    }

    public boolean isFull(){
        return tamanho == fila.length;
    }

    public void insert(T elemento){
        if(!isFull()){
            fila[tamanho++] = elemento;
        } else {
            throw new IllegalStateException("Lista cheia.");
        }
    }

    //Primeiro elemento inserido na fila
    public T peek(){
        return fila[0];
    }

    //Primeiro elemento inserido na fila e remove
    public T poll(){
        if (!isEmpty()){

            T elementoRemovido = fila[0];

            for (int i = 1; i < tamanho; i++) {
                fila[i - 1] = fila[i];
            }

            fila[tamanho - 1] = null;
            tamanho--;
            return elementoRemovido;
        }

        throw new IllegalStateException("Não á elementos para serem removidos");
//        return null;
    }

    public void exibe() {
        if (isEmpty()) {
            System.out.println("A fila está vazia.");
        } else {
            for (int i = 0; i < tamanho; i++) {
                System.out.print(fila[i] + " ");
            }
        }
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public void setFila(T[] fila) {
        this.fila = fila;
    }
}
