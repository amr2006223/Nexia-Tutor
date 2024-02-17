package com.nexia.nexia.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class CrudOperations<T, X, R extends JpaRepository<T, X>> { 
    //T is the type of class User, Game, Lesson etc...
    //X is the id type of that class String, Long, UUID etc..
    //R is the repository class of the T UserRepository, GameRepository, LessonRepository etc...
    protected final R repository;
    
    public CrudOperations(R repository) {
        this.repository = repository;
    }

    public T addEntity(T entity){
        try{
            repository.save(entity);
            return entity;
        }
        catch(Exception e){
            return null;
        }
    }
    public boolean deleteEntity(X id){
        T entity = repository.findById(id).orElse(null);
        if(entity == null) return false;
        try {
            repository.delete(entity);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public T updateEntity(T entity){
        if (entity == null) return null;
        try {
            T updateEntity = repository.save(entity);
            return updateEntity;
        } catch (Exception e) {
            return null;
        }
    }
    public T getEntityById(X id){
        try {
        T entity = repository.findById(id).orElse(null);
        if (entity == null) return null;
            return entity;
        } catch (Exception e) {
            return null;
        }
    }
    public List<T> getAllEntities(){
        try{
            List<T> allEntities = repository.findAll();
            if(allEntities.isEmpty() || allEntities == null) return null;
            return allEntities;
        }catch(Exception e){
            return null;
        }
    }
}
