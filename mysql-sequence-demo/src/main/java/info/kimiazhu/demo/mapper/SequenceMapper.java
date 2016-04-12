package info.kimiazhu.demo.mapper;

public interface SequenceMapper {
    
    public enum SEQ_NAME {
        APP_ID,
    }

    int nextValue(SEQ_NAME name);
    
    int currValue(SEQ_NAME name);
    
    void setValue(SEQ_NAME name, int val);

}

