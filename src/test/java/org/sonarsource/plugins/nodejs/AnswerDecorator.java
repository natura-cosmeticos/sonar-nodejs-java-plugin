package org.sonarsource.plugins.nodejs;

import java.util.LinkedList;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class  AnswerDecorator <T extends Object> implements Answer<T> {

    private LinkedList<T> answers = new LinkedList<>();

    public AnswerDecorator<T> add(T value) {
        answers.add(value);
        return this;
    }

    @Override
    public T answer(InvocationOnMock invocation) throws Throwable {
        return answers.removeFirst();
    }

}