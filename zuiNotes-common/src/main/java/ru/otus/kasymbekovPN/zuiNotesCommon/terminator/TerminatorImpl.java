package ru.otus.kasymbekovPN.zuiNotesCommon.terminator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;

public class TerminatorImpl implements Terminator {

    private static final Logger logger = LoggerFactory.getLogger(TerminatorImpl.class);

    private final TerminatorHandler terminatorHandler;

    public TerminatorImpl(TerminatorHandler terminatorHandler) {
        this.terminatorHandler = terminatorHandler;
    }

    @PreDestroy
    @Override
    public void onDestroy() {
        terminatorHandler.handle();
    }
}
