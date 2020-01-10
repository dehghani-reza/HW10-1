package ir.mctab.java32.hw10.repositories;

import ir.mctab.java32.hw10.entities.User;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface FindAll<T,V> {

    List<T> findByPredicate(Predicate<T> predicate);

    List<V> findByFunction(Function<T,V> function , User user);


}
