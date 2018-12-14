package fr.axonic.base.engine;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.axonic.observation.AEntityListener;
import fr.axonic.observation.binding.BindingParametersException;
import fr.axonic.observation.binding.BindingTypesException;
import fr.axonic.observation.event.ACollectionChangedEvent;
import fr.axonic.observation.event.AEntityChanged;
import fr.axonic.observation.event.AEntityChangedEvent;
import fr.axonic.observation.event.AEntityEvent;
import fr.axonic.observation.event.ChangedEventType;
import fr.axonic.util.StringHelper;
import fr.axonic.validation.Verifiable;
import fr.axonic.validation.exception.VerificationException;
import fr.axonic.visitor.AVisitor;

/**
 * @author cduffau
 * @author bgrabiec
 */
@XmlRootElement
public abstract class AEntity {

    protected static final Logger                 LOGGER           = LoggerFactory.getLogger(AEntity.class);

    private boolean                               accumulateEvents = false;

    private String                                path, code, label;
    private transient boolean                     persistent       = true;
    private transient PropertyChangeSupport       changeSupport;

    private transient AEntityListener             bindingListener;
    private transient AEntity                     bindElement;
    private transient final List<AEntityListener> listeners        = new CopyOnWriteArrayList();

    @XmlElement
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        setUnverifiableProperty(AVarProperty.CODE.name(), code);
    }

    @XmlElement
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        setUnverifiableProperty(AVarProperty.PATH.name(), path);
    }

    @XmlTransient
    public String getFullPath() {
        return getPath() + "." + getCode();
    }

    @XmlTransient
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        setUnverifiableProperty(AVarProperty.LABEL.name(), label);
    }

    protected void setUnverifiableProperty(String propertyName, Object newValue) {

        if (isPropertyVerifiable(propertyName)) {
            throw new IllegalArgumentException("The property " + propertyName + " is verifiable");
        }

        Object oldValue = getPropertyValue(propertyName);

        setPropertyValue(propertyName, newValue);

        this.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected boolean isPropertyVerifiable(String propertyName) {
        return false;
    }

    protected Object getPropertyValue(String propertyName) {
        Object result;
        switch (AVarProperty.valueOf(propertyName)) {
            case CODE: {
                result = code;
            }
                break;
            case LABEL: {
                result = label;
            }
                break;
            case PATH: {
                result = path;
            }
                break;
            default: {
                throw new UnknownPropertyException();
            }

        }
        return result;
    }

    protected void setPropertyValue(String propertyName, Object newPropertyValue) {
        switch (AVarProperty.valueOf(propertyName)) {
            case CODE: {
                code = (String) newPropertyValue;
            }
                break;
            case LABEL: {
                label = (String) newPropertyValue;
            }
                break;
            case PATH: {
                path = (String) newPropertyValue;
            }
                break;
            default: {
                throw new UnknownPropertyException();
            }
        }

    }

    /**
     * Set the value of a property with a given property name. There are to
     * cases:
     * <ol>
     * <li>The property is verifiable - the modification is performed in the
     * following steps: the property value is changed internally, the method
     * {@link Verifiable#verify(boolean)} is called. If the the modification is
     * correct, the appropriate listeners are notified. Otherwise modification
     * is reverted and the fr.axonic.validation exception is passed.</li>
     * <li>The property is unverifiable</li>
     * </ol>
     *
     * @param propertyName
     *            A valid property of this AVar
     * @param newValue
     *            A new value of the property
     * @throws VerificationException
     *             The exception is thrown only for the verifiable properties in
     *             case when their modification does not satisfy the
     *             fr.axonic.validation procedure of this AVar (see
     *             {@link Verifiable#verify(boolean)}).
     */
    public void setProperty(String propertyName, Object newValue) {

        Object oldValue = getPropertyValue(propertyName);

        setPropertyValue(propertyName, newValue);

        this.firePropertyChange(propertyName, oldValue, newValue);
        this.fireEvent(new AEntityChangedEvent(ChangedEventType.CHANGED, this, propertyName, oldValue, newValue));

    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null)
            return;
        if (changeSupport == null)
            changeSupport = new PropertyChangeSupport(this);
        changeSupport.addPropertyChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null || changeSupport == null)
            return;
        changeSupport.removePropertyChangeListener(listener);
    }

    @XmlTransient
    public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
        if (changeSupport == null)
            return new PropertyChangeListener[0];
        return changeSupport.getPropertyChangeListeners();
    }

    public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        if (listener == null)
            return;
        if (changeSupport == null)
            changeSupport = new PropertyChangeSupport(this);
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        if (listener == null || changeSupport == null)
            return;
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    protected void firePropertyChange(Enum<?> propertyEnum, Object oldValue, Object newValue) {
        firePropertyChange(propertyEnum.name(), oldValue, newValue);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {

        if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
            return;
        }

        PropertyChangeSupport changeSupport = this.changeSupport;
        if (changeSupport == null) {
            return;
        }
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    public synchronized void addListener(AEntityListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public synchronized void removeListener(AEntityListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    public synchronized void removeListeners() {
        listeners.clear();
    }

    private AEntityChanged accumulatedEvents;

    // @Loggable(mode = LoggingMode.SERIAL_TIMING, limit = 200)
    protected void fireEvent(AEntityChanged events) {

        if (isAccumulateEvents()) {
            accumulateEvents(events);
        } else {
            listeners.forEach(listener -> fireEvent(events, listener));
        }
    }

    private void fireEvent(AEntityChanged events, AEntityListener listener) {

        // Count the number of events to execute
        long changed = events.getEvents().stream().filter(event -> acceptEvent(event, listener)).count();

        if (changed != 0) {
            listener.changed(events);
        }
    }

    private boolean acceptEvent(AEntityEvent event, AEntityListener listener) {
        return listener.acceptChangedType(event.getChangedType())
                && (!listener.listenOnlyEffectiveChanges() || isEffectiveChange(event));
    }

    private boolean isEffectiveChange(AEntityEvent event) {
        return (event instanceof AEntityChangedEvent && isEffectiveChange((AEntityChangedEvent) event))
                || (event instanceof ACollectionChangedEvent && isEffectiveChange((ACollectionChangedEvent) event));
    }

    private void accumulateEvents(AEntityChanged events) {
        if (accumulatedEvents == null) {
            accumulatedEvents = new AEntityChanged(new ArrayList<>());
        }
        if (events != null) {
            accumulatedEvents.getEvents().addAll(events.getEvents());
        }
    }

    protected void fireEvent(AEntityEvent... events) {
        fireEvent(new AEntityChanged(events));
    }

    protected void fireEvent(ChangedEventType type, AEntityEvent... events) {
        fireEvent(new AEntityChanged(type, events));
    }

    public void accumulateEvents() {
        setAccumulateEvents(true);
    }

    public void freeEvents() {
        setAccumulateEvents(false);
    }

    private void setAccumulateEvents(boolean accumulateEvents) {
        if (this.accumulateEvents != accumulateEvents) {
            this.accumulateEvents = accumulateEvents;
            if (!accumulateEvents && accumulatedEvents != null) {
                fireEvent(accumulatedEvents);
                accumulatedEvents = null;
            }
        } else {
            // DO NOTHING
        }
    }

    @XmlTransient
    private boolean isAccumulateEvents() {
        return this.accumulateEvents;
    }

    /**
     * Bind this entity with the given one.
     * 
     * @param entity
     *            to bind with current one.
     * @param <S>
     *            entity type.
     */
    public abstract <S extends AEntity> void bind(S entity) throws BindingTypesException;

    /**
     * Unbind current entity and binded entity.
     */
    public abstract void unbind();

    public abstract <S extends AEntity> void bindBidirectional(S entity)
            throws BindingTypesException, VerificationException, BindingParametersException;

    public boolean isBindWith(AEntity entity) {
        return getBindElement() != null && entity != null && getBindElement() == entity;
    }

    @XmlTransient
    public abstract <S extends AEntity> void setValues(S entity) throws VerificationException, BindingTypesException;

    /**
     * Checks if value changed effectively or not.
     * 
     * @param event
     * @return
     */
    private boolean isEffectiveChange(AEntityChangedEvent event) {
        return (event.getOldValue() == null && event.getNewValue() != null)
                || (event.getOldValue() != null && !event.getOldValue().equals(event.getNewValue()));
    }

    /**
     * Only permutations are defined as non effective-changes in AList.
     * 
     * @param event
     * @return
     */
    private boolean isEffectiveChange(ACollectionChangedEvent event) {
        return true;
    }

    protected void finalize() throws Throwable {
        removeListeners();
        super.finalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AEntity))
            return false;

        AEntity aEntity = (AEntity) o;

        if (getPath() != null ? !getPath().equals(aEntity.getPath()) : aEntity.getPath() != null)
            return false;
        if (getCode() != null ? !getCode().equals(aEntity.getCode()) : aEntity.getCode() != null)
            return false;
        return getLabel() != null ? getLabel().equals(aEntity.getLabel()) : aEntity.getLabel() == null;

    }

    @Override
    public int hashCode() {
        int result = getPath() != null ? getPath().hashCode() : 0;
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getLabel() != null ? getLabel().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "path='" + path + '\'' + ", code='" + code + '\'' + ", label='" + label + '\'';
    }

    public String toString(int indentFactor) {
        return toString(StringHelper.repeatString(" ", indentFactor), 0);
    }

    protected String toString(String indentString, int level) {
        return toString();
    }

    @XmlTransient
    public AEntityListener getBindingListener() {
        return bindingListener;
    }

    public void setBindingListener(AEntityListener bindingListener) {
        this.bindingListener = bindingListener;
    }

    @XmlTransient
    public AEntity getBindElement() {
        return bindElement;
    }

    public void setBindElement(AEntity bindElement) {
        this.bindElement = bindElement;
    }

    @XmlTransient
    public boolean isBound() {
        return getBindElement() != null;
    }

    @XmlTransient
    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public abstract void accept(AVisitor v);
}
