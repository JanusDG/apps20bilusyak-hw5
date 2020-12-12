package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

public interface IntStream {

    Double average() throws IllegalAccessException;

    Integer max()  throws IllegalAccessException;

    Integer min()  throws IllegalAccessException;
    
    IntStream flatMap(IntToIntStreamFunction func);

    long count();

    IntStream filter(IntPredicate predicate);

    void forEach(IntConsumer action);

    IntStream map(IntUnaryOperator mapper);

    int reduce(int identity, IntBinaryOperator op);

    Integer sum() throws IllegalAccessException ;

    int[] toArray();
}
