package com.beckytech.simplequotes;

import java.util.List;

public interface QuoteResponseListener {
    void didFetch(List<QuoteResponse> responses, String message);
    void didError(String message);
}
