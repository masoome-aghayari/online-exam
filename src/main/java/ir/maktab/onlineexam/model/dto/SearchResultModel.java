package ir.maktab.onlineexam.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class SearchResultModel {
    List<UserDto> matchedUsers;
    List<String> roles;
    int pageNumber;
    long totalPages;
    UserDto userDto;
}
