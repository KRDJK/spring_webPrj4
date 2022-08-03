package com.project.web_prj.practice.login.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DuplicateChecker {

    private String type;
    private String value;
}
