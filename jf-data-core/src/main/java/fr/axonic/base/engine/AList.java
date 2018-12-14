package fr.axonic.base.engine;

import fr.axonic.visitor.AVisitor;
import fr.axonic.observation.AEntityListener;
import fr.axonic.observation.binding.BindingParametersException;
import fr.axonic.observation.binding.BindingTypesException;
import fr.axonic.observation.binding.BindingsBidirectional;
import fr.axonic.observation.binding.listener.AListSimpleBindingListener;
import fr.axonic.observation.event.ACollectionChangedEvent;
import fr.axonic.observation.event.AEntityChanged;
import fr.axonic.observation.event.AEntityEvent;
import fr.axonic.observation.event.ChangedEventType;
import fr.axonic.util.StringHelper;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

/**
 * Created by cduffau on 11/07/16.
 */
@XmlRootElement
@XmlSeeAlso(List.class)
public class AList<T extends AEntity> extends AEntity implements List<T> {

    private List<T>               list;
    @XmlTransient
    private final AEntityListener internalListener;

    public AList(List<T> entities) {
        this.list = entities;

        internalListener = new AEntityListener() {

            @Override
            public void changed(AEntityChanged events) {
                fireEvent(events);
            }

            @Override
            public boolean listenOnlyEffectiveChanges() {
                return false;
            }

            @Override
            public boolean acceptChangedType(ChangedEventType changedEventType) {
                return true;
            }
        };

        entities.forEach(entity -> entity.addListener(internalListener));
    }

    public AList(T... t) {
        this(Arrays.asList(t));
    }

    public AList() {
        this(new ArrayList<>());
    }

    @XmlElement
    public List<T> getList() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        boolean result = list.add(t);
        if (result) {
            t.addListener(internalListener);
            fireEvent(new ACollectionChangedEvent<>(ChangedEventType.ADDED, this, t));
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = list.remove(o);
        if (result && o != null && o instanceof AEntity) {
            ((AEntity) o).removeListener(internalListener);
            fireEvent(new ACollectionChangedEvent<>(ChangedEventType.REMOVED, this, (AEntity) o, list.indexOf(o)));
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean result = list.addAll(c);

        if (result) {
            List<AEntityEvent> events = new ArrayList<>();
            for (T entity : c) {
                entity.addListener(internalListener);
                events.add(new ACollectionChangedEvent<>(ChangedEventType.ADDED, this, entity));
            }

            fireEvent(new AEntityChanged(events));
        }

        return result;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {

        boolean result = list.addAll(index, c);

        if (result) {
            List<AEntityEvent> events = new ArrayList<>();
            int i = 0;
            for (T entity : c) {
                entity.addListener(internalListener);
                events.add(new ACollectionChangedEvent<>(ChangedEventType.ADDED, this, entity, index + (i++)));
            }

            fireEvent(new AEntityChanged(events));
        }

        return result;
    }

    @Override
    // FIXME listener
    public boolean removeAll(Collection<?> c) {

        List<AEntityEvent> events = new ArrayList<>();

        IntStream.range(0, list.size()).filter(listIndex -> c.contains(list.get(listIndex))).forEach(listIndex -> {
            list.get(listIndex).removeListener(internalListener);
            events.add(new ACollectionChangedEvent<>(ChangedEventType.REMOVED, this, list.get(listIndex)));
        });

        fireEvent(new AEntityChanged(events));

        return list.removeAll(c);
    }

    @Override
    // FIXME listener
    public boolean retainAll(Collection<?> c) {

        List<AEntityEvent> events = new ArrayList<>();

        IntStream.range(0, list.size()).filter(listIndex -> !c.contains(list.get(listIndex))).forEach(listIndex -> {
            list.get(listIndex).removeListener(internalListener);
            events.add(new ACollectionChangedEvent<>(ChangedEventType.REMOVED, this, list.get(listIndex)));
        });

        fireEvent(new AEntityChanged(events));

        return list.retainAll(c);
    }

    @Override
    public void clear() {
        List<AEntityEvent> events = new ArrayList<>();

        List<T> tempList = new ArrayList<>();
        tempList.addAll(list);
        list.clear();

        for (int i = 0; i < tempList.size(); i++) {
            tempList.get(i).removeListener(internalListener);
            events.add(new ACollectionChangedEvent<>(ChangedEventType.REMOVED, this, tempList.get(i)));
        }

        tempList.clear();
        fireEvent(new AEntityChanged(events));

    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        T previousElement = list.set(index, element);
        fireEvent(new ACollectionChangedEvent<>(ChangedEventType.CHANGED, this, element, index));
        return previousElement;
    }

    @Override
    public void add(int index, T element) {
        element.addListener(internalListener);
        list.add(index, element);
        fireEvent(new ACollectionChangedEvent<>(ChangedEventType.ADDED, this, element, index));
    }

    @Override
    public T remove(int index) {
        T previousElement = list.remove(index);
        if (list.get(index) != null) {
            list.get(index).removeListener(internalListener);
            fireEvent(new ACollectionChangedEvent<>(ChangedEventType.REMOVED, this, previousElement, index));
        }
        return previousElement;
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public void sort(Comparator<? super T> c) {

        list.sort(c);

        List<AEntityEvent> events = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            events.add(new ACollectionChangedEvent<>(ChangedEventType.PERMUTED, this, list.get(i), i));
        }

        fireEvent(new AEntityChanged(ChangedEventType.PERMUTED, events));
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {

        list.replaceAll(operator);

        List<AEntityEvent> events = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            events.add(new ACollectionChangedEvent<>(ChangedEventType.CHANGED, this, list.get(i), i));
        }

        fireEvent(new AEntityChanged(events));
    }

    @Override
    public <S extends AEntity> void bind(S entity) throws BindingTypesException {

        if (entity == null) {
            throw new NullPointerException();
        }

        if (!this.getClass().isAssignableFrom(entity.getClass())) {
            throw new BindingTypesException("Given entity type doesn't match to current list type");
        }

        setBindElement(entity);
        AList<T> bindAList = (AList<T>) entity;

        try {
            setValues(bindAList);
        } catch (VerificationException e) {
            LOGGER.error(e.getMessage(), e);
        }

        AListSimpleBindingListener<T> simpleBindingListener = new AListSimpleBindingListener<>(this);

        entity.addListener(simpleBindingListener);
        entity.setBindingListener(simpleBindingListener);

    }

    @Override
    public <S extends AEntity> void bindBidirectional(S entity)
            throws BindingTypesException, VerificationException, BindingParametersException {

        BindingsBidirectional.bindBiDirectional(this, entity);

    }

    @Override
    public void unbind() {
        if (getBindElement() != null) {
            AList<T> bindElt = (AList<T>) getBindElement();

            getBindElement().removeListener(getBindElement().getBindingListener());
            getBindElement().setBindingListener(null);

            setBindElement(null);

            if (bindElt.isBindWith(this)) {
                bindElt.unbind();
            }
        }
    }

    @Override
    public <S extends AEntity> void setValues(S entity) throws VerificationException {
        if (entity instanceof AList) {
            clear();
            ((AList<T>) entity).forEach(this::add);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AList))
            return false;
        if (!super.equals(o))
            return false;

        AList<?> aList = (AList<?>) o;

        boolean res = getList().equals(aList.getList());
        return res;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getList() != null ? getList().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AList{" + "list=" + list + '}';
    }

    @Override
    protected String toString(String indentString, int level) {
        StringBuilder stringBuilder = new StringBuilder();

        String indent = StringHelper.repeatString(indentString, level);
        String indent2 = indent + indentString;

        stringBuilder.append("(\n");
        if (list != null) {
            for (T element : list) {
                stringBuilder.append(indent2 + element.toString(indentString, level + 1) + "\n");
            }
        }
        return stringBuilder.append(indent + ")").toString();
    }

    @Override
    public void accept(AVisitor v) {
        v.visit(this);
    }

}
