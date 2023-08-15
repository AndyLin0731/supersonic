package com.tencent.supersonic.util;

import static java.time.LocalDate.now;

import com.tencent.supersonic.auth.api.authentication.pojo.User;
import com.tencent.supersonic.chat.api.pojo.SchemaElement;
import com.tencent.supersonic.chat.api.pojo.SchemaElementType;
import com.tencent.supersonic.chat.api.pojo.request.QueryFilter;
import com.tencent.supersonic.chat.api.pojo.request.QueryReq;
import com.tencent.supersonic.common.pojo.DateConf;
import com.tencent.supersonic.semantic.api.query.enums.FilterOperatorEnum;
import java.util.Set;

public class DataUtils {

    private static final User user_test = new User(1L, "admin", "admin", "admin@email");

    public static User getUser() {
        return user_test;
    }

    public static QueryReq getQueryContextReq(Integer id, String query) {
        QueryReq queryContextReq = new QueryReq();
        queryContextReq.setQueryText(query);//"alice的访问次数"
        queryContextReq.setChatId(id);
        queryContextReq.setUser(user_test);
        return queryContextReq;
    }

    public static SchemaElement getSchemaElement(String name) {
        return SchemaElement.builder()
                .name(name)
                .build();
    }

    public static SchemaElement getMetric(Long modelId, Long id, String name, String bizName) {
        return SchemaElement.builder()
                .model(modelId)
                .id(id)
                .name(name)
                .bizName(bizName)
                .useCnt(0L)
                .type(SchemaElementType.METRIC)
                .build();
    }

    public static SchemaElement getDimension(Long modelId, Long id, String name, String bizName) {
        return SchemaElement.builder()
                .model(modelId)
                .id(id)
                .name(name)
                .bizName(bizName)
                .useCnt(null)
                .type(SchemaElementType.DIMENSION)
                .build();
    }

    public static QueryFilter getFilter(String bizName, FilterOperatorEnum filterOperatorEnum, Object value,
            String name,
            Long elementId) {
        QueryFilter filter = new QueryFilter();
        filter.setBizName(bizName);
        filter.setOperator(filterOperatorEnum);
        filter.setValue(value);
        filter.setName(name);
        filter.setElementID(elementId);
        return filter;
    }

    public static DateConf getDateConf(Integer unit, DateConf.DateMode dateMode, String period) {
        DateConf dateInfo = new DateConf();
        dateInfo.setUnit(unit);
        dateInfo.setDateMode(dateMode);
        dateInfo.setPeriod(period);
        dateInfo.setStartDate(now().plusDays(-unit).toString());
        dateInfo.setEndDate(now().toString());
        return dateInfo;
    }

    public static DateConf getDateConf(DateConf.DateMode dateMode, Integer unit, String period, String startDate,
            String endDate) {
        DateConf dateInfo = new DateConf();
        dateInfo.setUnit(unit);
        dateInfo.setDateMode(dateMode);
        dateInfo.setPeriod(period);
        dateInfo.setStartDate(startDate);
        dateInfo.setEndDate(endDate);
        return dateInfo;
    }

    public static DateConf getDateConf(DateConf.DateMode dateMode, String startDate, String endDate) {
        DateConf dateInfo = new DateConf();
        dateInfo.setDateMode(dateMode);
        dateInfo.setStartDate(startDate);
        dateInfo.setEndDate(endDate);
        return dateInfo;
    }

    public static Boolean compareDate(DateConf dateInfo1, DateConf dateInfo2) {
        Boolean timeFilterExist = dateInfo1.getUnit().equals(dateInfo2.getUnit()) &&
                dateInfo1.getDateMode().equals(dateInfo2.getDateMode()) &&
                dateInfo1.getPeriod().equals(dateInfo2.getPeriod());
        return timeFilterExist;
    }

    public static Boolean compareDateDimension(Set<SchemaElement> dimensions) {
        SchemaElement schemaItemDimension = new SchemaElement();
        schemaItemDimension.setBizName("sys_imp_date");
        Boolean dimensionExist = false;
        for (SchemaElement schemaItem : dimensions) {
            if (schemaItem.getBizName().equals(schemaItemDimension.getBizName())) {
                dimensionExist = true;
            }
        }
        return dimensionExist;
    }

    public static Boolean compareDimensionFilter(Set<QueryFilter> dimensionFilters, QueryFilter dimensionFilter) {
        Boolean dimensionFilterExist = false;
        for (QueryFilter filter : dimensionFilters) {
            if (filter.getBizName().equals(dimensionFilter.getBizName()) &&
                    filter.getOperator().equals(dimensionFilter.getOperator()) &&
                    filter.getValue().toString().equals(dimensionFilter.getValue().toString()) &&
                    filter.getElementID().equals(dimensionFilter.getElementID()) &&
                    filter.getName().equals(dimensionFilter.getName())) {
                dimensionFilterExist = true;
            }
        }
        return dimensionFilterExist;
    }

}