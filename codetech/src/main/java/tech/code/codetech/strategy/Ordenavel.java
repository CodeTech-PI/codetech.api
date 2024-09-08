package tech.code.codetech.strategy;

import java.util.List;

public interface Ordenavel {

   <E extends Comparable<E>> List<E> ordenar(List<E> lista);

}
