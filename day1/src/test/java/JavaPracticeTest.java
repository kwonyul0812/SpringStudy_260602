import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JavaPracticeTest {
    public static void main(String[] args) {
        int sum = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0)
                .sum();

        System.out.println("1부터 100중 짝수의 합: " + sum);

        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "a", "b", "c"));
        List<String> resultList = list.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        System.out.println(resultList.toString());


        TransactionStatus s = TransactionStatus.fromCode("02");
        System.out.println(s.getDesc());

        LocalDate date = LocalDate.now();
        String today = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        System.out.println("오늘의 날짜는 : " + today);

    }

    public enum TransactionStatus {
        APPROVED("00", "정상승인"),
        REJECT("02", "거절"),
        WITHDRAW("20", "환불");

        private final String code;
        private final String desc;

        TransactionStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        // 코드로 enum 찾기
        public static TransactionStatus fromCode(String code) {
            for (TransactionStatus s : values()) {
                if (s.code.equals(code)) return s;
            }
            throw new IllegalArgumentException("Unknown: " + code);
        }
    }
}
