package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;

public class AsIntStream implements IntStream
{
    private static int[] container;

    private AsIntStream(int[] container)
    {
        AsIntStream.container = container;
    }

    // створює початковий потік на основі масиву цілих чисел
    public static IntStream of(int... values)
    {
        container = new int[values.length];
        System.arraycopy(values, 0, container, 0, values.length);
        return new AsIntStream(container);
    }

    private int[] fromArray(ArrayList<Integer> array)
    {
        int[] result = new int[array.size()];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = array.get(i);
        }
        return result;
    }

    public boolean isEmpty()
    {
        return container.length == 0;
    }

    private void checkNotEmpty() throws IllegalAccessException
    {
        if (container.length == 0) {throw new IllegalAccessException("Empty stream");}
    }

    // середнє значення чисел в потоці. Термінальній метод. IllegalArgumentException - if empty
    @Override
    public Double average() throws IllegalAccessException
    {
        checkNotEmpty();
        return ((double) sum()) / ((double) container.length);
    }

    // максимальне значення числа в потоці. Термінальній метод. IllegalArgumentException - if empty
    @Override
    public Integer max() throws IllegalAccessException
    {
        checkNotEmpty();
        int max = container[0];
        for (int val : container)
        {
            if (max < val) { max = val;}
        }
        return max;
    }

    // мінімальне значення числа в потоці. Термінальній метод. IllegalArgumentException - if empty
    @Override
    public Integer min() throws IllegalAccessException
    {
        checkNotEmpty();
        int min = container[0];
        for (int val : container)
        {
            if (min > val) {min = val;};
        }
        return min;
    }

    // кількість значень (елементів) в потоці. Термінальній метод.
    @Override
    public long count()
    {
        return container.length;
    }

    // сума всіх значень в потоці. Термінальній метод. IllegalArgumentException - if empty
    @Override
    public Integer sum() throws IllegalAccessException
    {
        checkNotEmpty();
        int sum = 0;
        for (int val : container) {sum += val;}
        return sum;
    }

    // для кожного значення з потоку виконує операцію зазначену в реалізації IntConsumer. Даний метод є термінальним і нічого не повертає
    @Override
    public void forEach(IntConsumer action)
    {
        for (int val : container)
        {
            action.accept(val);
        }
    }

    // для кожного значення з потоку перевіряє його на предмет чи задовольняє воно умові в реалізації
    // IntPredicate, якщо так - повертає його в результуючий потік, якщо ні - викидає
    @Override
    public IntStream filter(IntPredicate predicate)
    {
        ArrayList<Integer> filtered = new ArrayList<>();
        for (int val : container)
        {
            if (predicate.test(val)) {filtered.add(val);}
        }
        int[] result = fromArray(filtered);
        return of(result);
    }

    // застосовує до кожного зі значень потоку реалізацію IntUnaryOperator і повертає його в результуючий потік
    @Override
    public IntStream map(IntUnaryOperator mapper)
    {
        int[] mapped = new int[container.length];
        for (int i = 0; i < container.length; i++)
        {
            mapped[i] = mapper.apply(container[i]);
        }
        return of(mapped);
    }

    // застосовує до кожного зі значень потоку реалізацію IntToIntStreamFunction,
    // яка на основі кожного зі значення створює новий потік значень, які потім
    // об'єднуються в один результуючий потік (см. http://java.amitph.com/2014/02/java-8-streams-api-intermediate.html)
    @Override
    public IntStream flatMap(IntToIntStreamFunction func)
    {
        ArrayList<Integer> flatMapped = new ArrayList<>();
        for (int val : container)
        {
            int[] flatMappedArray = func.applyAsIntStream(val).toArray();
            for (int mappedVal : flatMappedArray) {flatMapped.add(mappedVal);}
        }
        int[] result = fromArray(flatMapped);
        return of(result);
    }

    // виконує згортку значень потоку в ціле число, початкове значення задається identity, функція згортки - в реалізації IntBinaryOperator. Термінальній метод.
    @Override
    public int reduce(int identity, IntBinaryOperator op)
    {
        int answer = identity;
        for (int val : container) {answer = op.apply(answer, val);}
        return answer;
    }

    // повертає потік у вигляді масиву. Термінальній метод.
    @Override
    public int[] toArray()
    {
        return container.clone();
    }

}
