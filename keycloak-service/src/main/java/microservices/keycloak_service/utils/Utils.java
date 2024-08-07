package microservices.keycloak_service.utils;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
}
