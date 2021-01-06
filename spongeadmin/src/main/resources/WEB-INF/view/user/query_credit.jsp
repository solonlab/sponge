<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm style2">
<head>
    <title>${app} - 信用报告</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <style>
        tr.t5{text-decoration:line-through}
        tr a{text-decoration:none}
        tr.ex,tr.ex a{color: red!important;}
    </style>
</head>
<body>
<ct:header/>
<main class="sml">
    <ct:toolmenu pack="sponge_user_d"></ct:toolmenu>

    <detail>
        <section>
            <header>
                <h2>芝麻信用</h2>
            </header>
            <article>
                <div>芝麻信用分：${zmScore == null ? "-" :  zmScore}</div>
                <br/>
                <div>申请欺诈评分：${zmAntifraudScore == null ? "-" :  zmAntifraudScore}</div>

            <c:if test="${antifraudRisks != null}">
                <br/>
                <div>欺诈关注名单：</div>
                <datagrid>
                    <table style="width: 50%;">
                        <thead>
                        <tr>
                            <th>风险说明</th>
                            <th>是否命中</th>
                            <th>风险等级</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="antifraudRisk" items="${antifraudRisks}" varStatus="status">
                            <tr>
                                <td>${antifraudRisk.describe}</td>
                                <td>是</td>
                                <td>${antifraudRisk.riskLevel}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </datagrid>
            </c:if>
            <c:if test="${watchList != null}">
                <br/>
                <div>行业关注名单：</div>
                <datagrid>
                    <table style="width: 90%;">
                        <thead>
                        <tr>
                            <th>行业类型名称</th>
                            <th>风险类型名称</th>
                            <th>风险名称</th>
                            <th>数据更新时间</th>
                            <th>结清状态</th>
                            <th>详细信息</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="watch" items="${watchList}" varStatus="status">
                            <tr>
                                <td>${watch.biz_code}</td>
                                <td>${watch.type}</td>
                                <td>${watch.code}</td>
                                <td>${watch.refreshDate}</td>
                                <td><c:out value="${fn:toUpperCase(watch.settlement)}"/></td>
                                <td>逾期金额（元）：[${watch.overDueAmount}]，最近一次违约时间：${watch.breakDate}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </datagrid>
            </c:if>
            </article>
        </section>
        <section>
            <header>
                <h2>同盾</h2>
            </header>
            <article>
            <c:if test="${tdReport != null}">
                <div>同盾分：${tdReport.score}</div>
                <br/>
                <div>基本信息：</div>
                <datagrid>
                    <table style="width: 60%;">
                        <thead>
                        <tr>
                            <th>报告编号</th>
                            <th>风险分数</th>
                            <th>查询时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${tdReport.reportId}</td>
                            <td>${tdReport.score}</td>
                            <td><fmt:formatDate value="${tdReport.queryDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
                <br/>
                <div>命中项：</div>
                <datagrid>
                    <table style="width: 90%;">
                        <thead>
                        <tr>
                            <th>大类</th>
                            <th>命中规则</th>
                            <th>命中详情</th>
                            <th>风险结果</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="risk" items="${tdReport.platform}" varStatus="status">
                            <tr>
                                <td>多头借贷类</td>
                                <td>${risk.hitRule}</td>
                                <td>${risk.hitDetail}</td>
                                <td>${risk.riskLevel}</td>
                            </tr>
                        </c:forEach>
                        <c:forEach var="risk" items="${tdReport.discredit}" varStatus="status">
                            <tr>
                                <td>信贷逾期类</td>
                                <td>${risk.hitRule}</td>
                                <td>${risk.hitDetail}</td>
                                <td>${risk.riskLevel}</td>
                            </tr>
                        </c:forEach>
                        <c:forEach var="risk" items="${tdReport.crossEvent}" varStatus="status">
                            <tr>
                                <td>跨事件频度统计</td>
                                <td>${risk.hitRule}</td>
                                <td>${risk.hitDetail}</td>
                                <td>${risk.riskLevel}</td>
                            </tr>
                        </c:forEach>
                        <c:forEach var="risk" items="${tdReport.others}" varStatus="status">
                            <tr>
                                <td>其他</td>
                                <td>${risk.hitRule}</td>
                                <td>${risk.hitDetail}</td>
                                <td>${risk.riskLevel}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </datagrid>
            </c:if>
            <c:if test="${tdReport == null}">
                <table>
                    <tr>
                        <td>认证状态：</td>
                        <td>未认证</td>
                    </tr>
                </table>
            </c:if>
            </article>
        </section>
        <section>
            <header>
                <h2>邦盛风险评估</h2>
            </header>
            <article>
            <datagrid>

                <div style="overflow: hidden;">
                邦盛分:<fmt:formatNumber type="number" value="${risks_bs.score}" maxFractionDigits="0"/>
                    <div style="float:right;margin-right: 10px">
                        最近更新时间:<a><fmt:formatDate value="${risks_bs.utime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
                        首次提交时间:<a><fmt:formatDate value="${risks_bs.ctime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
                    </div>
                </div>
                <br/>
                <div>命中项详情:</div>
                <table id="bill_table" style="width: 100%">
                    <thead>
                    <td>命中分</td>
                    <td>分组</td>
                    <td>规则名称</td>
                    <td>风控策略</td>
                    </thead>
                    <tbody>
                    <c:forEach var="m" items="${risks_bs.risks}">
                        <tr>
                            <td><fmt:formatNumber type="number" value="${m.score}" maxFractionDigits="0"/></td>
                            <td>${m.group}</td>
                            <td>${m.ruleName}</td>
                            <td>
                                <c:if test="${m.ruleType == 1}">
                                    评分规则
                                </c:if>
                                <c:if test="${m.ruleType == 0}">
                                    ${m.policyName}
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </datagrid>
            </article>
        </section>

        <section>
            <header>
                <h2>众马风险评估</h2>
            </header>
            <article>
                <datagrid>

                    <div style="overflow: hidden;">
                        评估分:<fmt:formatNumber type="number" value="${risks_rubber.score}" maxFractionDigits="0"/>
                        <div style="float:right;margin-right: 10px">
                            最近更新时间:<a><fmt:formatDate value="${risks_rubber.utime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
                            首次提交时间:<a><fmt:formatDate value="${risks_rubber.ctime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
                        </div>
                    </div>
                    <br/>
                    <div>命中项详情:</div>
                    <table style="width: 100%">
                        <thead>
                            <td width="50px">命中分</td>
                            <td width="160px">分组</td>
                            <td>规则名称</td>
                            <td width="110px">触发值</td>
                            <td width="80px">评估建议</td>
                        </thead>
                        <tbody>
                        <c:forEach var="m" items="${risks_rubber.details}">
                            <c:if test="${m.m}"><tr></c:if>
                            <c:if test="${!m.m}"><tr class="${empty m.v?"t5 ex":"t5"}"></c:if>
                                <td>${m.n}</td>
                                <td>${m.s}</td>
                                <td>${m.r}</td>
                                <td class="left break">${m.v}</td>
                                <td class="right<c:if test="${m.a<0}"> t4</c:if>">${rubber.getEvaluationEnum(m.a)}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </datagrid>
            </article>
        </section>

        <section>
            <header>
                <h2>设备指纹</h2>
            </header>
            <article>
            <datagrid>
                <table style="width: 100%;">
                    <thead>
                    <tr>
                        <th style="width: 15%;">时间</th>
                        <th style="width: 10%;">GPS</th>
                        <th style="width: 10%;">设备型号</th>
                        <th style="width: 35%;">设备指纹</th>
                        <th style="width: 25%;">地址</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="m" items="${device_log}">
                        <tr>
                            <td><fmt:formatDate value="${m.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${m.gps}</td>
                            <td>${m.device_name}</td>
                            <td>${m.device_udid}</td>
                            <td>${m.address}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </datagrid>
            </article>
        </section>
    </detail>
</main>
</body>
</html>