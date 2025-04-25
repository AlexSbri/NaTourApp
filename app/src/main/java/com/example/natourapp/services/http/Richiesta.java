package com.example.natourapp.services.http;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public interface Richiesta<T> {
    String HOST = "HOST";
    List<T> retrieve() throws IOException;
    Long count() throws IOException;
    String add(T item) throws IOException;
    List<T> deserializeObjectsToList(Reader reader);
}
