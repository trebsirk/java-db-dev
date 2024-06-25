package services;

import java.util.List;

import DAOs.DAO;
import models.CrudLogEvent;
import models.CrudResult;

public class CrudService<T> {

    private final DAO<T> dao;

    public CrudService(DAO<T> dao) {
        this.dao = dao;
    }

    public T getById(int id) {
        return dao.getById(id);
    }

    public List<T> getAll() {
        return dao.getAll();
    }

    public CrudResult create(T t, CrudLogEvent logEvent) {
        long startTime = System.currentTimeMillis();
        CrudResult res = dao.add(t);
        long endTime = System.currentTimeMillis();
        logEvent = logEvent.withSuccess(res.success());
        logEvent = logEvent.withMsg(res.errorMessage().orElseGet(() -> ""));
        logEvent = logEvent.withExecutionTimeMs(endTime-startTime);
        System.out.println("SERVICE " + logEvent);
        return res;
    }

    public CrudResult update(T t, CrudLogEvent logEvent) {
        long startTime = System.currentTimeMillis();
        CrudResult res = dao.update(t);
        long endTime = System.currentTimeMillis();
        logEvent = logEvent.withSuccess(res.success());
        logEvent = logEvent.withMsg(res.errorMessage().orElseGet(() -> ""));
        logEvent = logEvent.withExecutionTimeMs(endTime-startTime);
        System.out.println("SERVICE " + logEvent);
        return res;
    }

    public CrudResult delete(int id, CrudLogEvent logEvent) {
        long startTime = System.currentTimeMillis();
        CrudResult res = dao.delete(id);
        long endTime = System.currentTimeMillis();
        logEvent = logEvent.withSuccess(res.success());
        logEvent = logEvent.withMsg(res.errorMessage().orElseGet(() -> ""));
        logEvent = logEvent.withExecutionTimeMs(endTime-startTime);
        System.out.println("SERVICE " + logEvent);
        return res;
    }

    public T fromJSON(String jsonString) {
        return dao.fromJSON(jsonString).get();
    }

    public String toJSON(T t) {
        return dao.toJSON(t);
    }
}
