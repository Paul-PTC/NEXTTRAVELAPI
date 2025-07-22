package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EstadoEntities;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class EstadoRepository implements JpaRepository<EstadoEntities, Integer> {
    @Override
    public void flush() {

    }

    @Override
    public <S extends EstadoEntities> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends EstadoEntities> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<EstadoEntities> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public EstadoEntities getOne(Integer integer) {
        return null;
    }

    @Override
    public EstadoEntities getById(Integer integer) {
        return null;
    }

    @Override
    public EstadoEntities getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends EstadoEntities> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends EstadoEntities> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends EstadoEntities> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends EstadoEntities> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends EstadoEntities> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends EstadoEntities> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends EstadoEntities, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends EstadoEntities> S save(S entity) {
        return null;
    }

    @Override
    public <S extends EstadoEntities> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<EstadoEntities> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<EstadoEntities> findAll() {
        return List.of();
    }

    @Override
    public List<EstadoEntities> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(EstadoEntities entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends EstadoEntities> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<EstadoEntities> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<EstadoEntities> findAll(Pageable pageable) {
        return null;
    }
}
