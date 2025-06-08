package tech.code.codetech.api.controller.generico.pilha;

public class PilhaObj<T> {

    private T[] pilha;
    private int topo;

    public PilhaObj(int capacidade) {
        this.pilha = (T[]) new Object[capacidade];
        this.topo = -1;
    }

    public boolean isEmpty() {
        return topo == -1;
    }

    public boolean isFull() {
        return topo == pilha.length - 1;
    }

    public void push(T info) {
        if (isFull()) {
            throw new IllegalStateException("A pilha está cheia.");
        }
        topo++;
        pilha[topo] = info;
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Não há nada para ser excluído.");
        }

        return pilha[topo--];

    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Não há nada para ser exibido.");
        }

        return pilha[topo];
    }

    public void exibe() {
        if (isEmpty()) {
            System.out.printf("Pilha vazia");
        } else {

            for (int i = topo; i >= 0; i--) {
                System.out.println(pilha[i]);
            }
        }
    }

    public int getTopo() {
        return topo;
    }

}