package cat.itacademyS5_01.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

public class LongIdConverter {

    @ReadingConverter
    public static class LongToIntegerConverter implements Converter<Long, Integer> {
        @Override
        public Integer convert(Long source) {
            return source != null ? source.intValue() : null;
        }
    }

    @WritingConverter
    public static class IntegerToLongConverter implements Converter<Integer, Long> {
        @Override
        public Long convert(Integer source) {
            return source != null ? source.longValue() : null;
        }
    }
}