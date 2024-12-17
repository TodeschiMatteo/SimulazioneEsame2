package a02b.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * {@inheritDoc}
 */
public class CursorHelpersImpl implements CursorHelpers{

    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        Cursor<X> cursor = new Cursor<X>() {
            final List<X> values = new LinkedList<>(list);
            int index=0;
            @Override
            public X getElement() {
                return this.values.get(index);
            }
            @Override
            public boolean advance() {
                if (this.index  < this.values.size()-1){
                    index++;
                    return true;
                } else {
                    return false;
                }
            }
            
        };
        return cursor;
    }

    @Override
    public Cursor<Integer> naturals() {
        Cursor<Integer> cursor = new Cursor<Integer>() {
            int index=0;
            @Override
            public Integer getElement() {
                return index;
            }
            @Override
            public boolean advance() {
                index++;
                return true;
            }
            
        };
        return cursor;
    }

    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        Cursor<X> cursor = new Cursor<X>() {
            final Cursor<X> values = input;
            int index = 0;
            int maxIndex = max;
            @Override
            public X getElement() {
                return values.getElement();
            }
            @Override
            public boolean advance() {
                if (this.index  < this.maxIndex-1 && values.advance()){
                    index++;
                    return true;
                } else {
                    return false;
                }
            }
        };
        return cursor;
    }

    @Override
    public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
        boolean isEnded = false;
        while(!isEnded){
            consumer.accept(input.getElement());
            if(!input.advance()){
                isEnded=true;
            }
        }
    }

    @Override
    public <X> List<X> toList(Cursor<X> input, int max) {
        final List<X> list = new LinkedList<>();
        boolean isEnded = false;
        for (int index=0; index<max && !isEnded; index++){
            list.add(index, input.getElement());
            if(!input.advance()){
                isEnded=true;
            }
        }
        return list;
    }
    
}
