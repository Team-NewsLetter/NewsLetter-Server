package com.swacademy.newsletter.validation.validator;

import com.swacademy.newsletter.apiPayload.code.status.ErrorStatus;
import com.swacademy.newsletter.service.newsTag.NewsTagCommandService;
import com.swacademy.newsletter.validation.annotation.ExistNewsTags;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsTagsExistValidator implements ConstraintValidator<ExistNewsTags, List<Long>> {
    private final NewsTagCommandService newsTagCommandService;

    @Override
    public void initialize(ExistNewsTags constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
        boolean isValid = newsTagCommandService.existsAllByIds(values);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.NEWS_TAG_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;

    }
}
