package microservices.keycloak_service.utils;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static Page<String> paginate(Map<String, Object> params) {
        Integer pageNo = (Integer) params.get("pageNo");
        Integer pageSize = (Integer) params.get("pageSize");
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List data = (List) params.get("data");
        List<String> list;
        int startItem = pageNo * pageSize;

        if(data.size() < startItem) {
            list = new ArrayList<>();
        }
        else {
            int toIndex = Math.min(startItem + pageSize, data.size());
            list = data.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, pageable, data.size());
    }

    public static String stringSubstitution(String template, Map<String, Object> newValues) {
        StringSubstitutor sub = new StringSubstitutor(newValues);
        String newTemplate = sub.replace(template);
        return newTemplate;
    }

    public static boolean isValidPassword(String password, Integer length) {
        if(password.length() < length) {
            return false;
        }

        String regexPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#\\$&*~]).{8,}$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
