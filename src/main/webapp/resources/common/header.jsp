<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resources/common/taglib.jsp"%>

<fmt:message var="label_sidebarbrand" key="label.sidebarbrand"
	bundle="${messages}" />

<fmt:message var="label_requirement" key="label.requirement"
	bundle="${messages}" />
<fmt:message var="label_requirement1" key="label.requirement1"
	bundle="${messages}" />
<fmt:message var="label_requirement2" key="label.requirement2"
	bundle="${messages}" />
<fmt:message var="label_requirement3" key="label.requirement3"
	bundle="${messages}" />
<fmt:message var="label_requirement4" key="label.requirement4"
	bundle="${messages}" />
<fmt:message var="label_requirement5" key="label.requirement5"
	bundle="${messages}" />
<fmt:message var="label_requirement6" key="label.requirement6"
	bundle="${messages}" />
<fmt:message var="label_requirement7" key="label.requirement7"
	bundle="${messages}" />
<fmt:message var="label_requirement8" key="label.requirement8"
	bundle="${messages}" />


<fmt:message var="label_advanced" key="label.advanced"
	bundle="${messages}" />
<fmt:message var="label_advanced1" key="label.advanced1"
	bundle="${messages}" />

<div
	class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
	id="accordionSidebar">

	<!-- Sidebar Brand -->
	<div
		class="sidebar-brand 
		d-flex align-items-center justify-content-center sidebar-brand-text
		 mx-3">${label_sidebarbrand}</div>

	<!-- Divider -->
	<hr class="sidebar-divider">

	<!-- Heading -->
	<div class="sidebar-heading">${label_requirement}</div>

	<ul class="navbar-nav ">
		<li class="nav-item  "><a class="nav-link"> <i
				class="fas fa-fw fa-check"></i> <span>${label_requirement1}</span></a></li>

		<li class="nav-item"><a class="nav-link"> <i
				class="fas far fa-check"></i> <span>${label_requirement2}</span></a></li>

		<li class="nav-item"><a class="nav-link"> <i
				class="fas far fa-check"></i> <span>${label_requirement3}</span></a></li>

		<li class="nav-item"><a class="nav-link"> <i
				class="fas far fa-check"></i> <span>${label_requirement4}</span></a></li>

		<li class="nav-item"><a class="nav-link"> <i
				class="fas far fa-check"></i> <span>${label_requirement5}</span></a></li>

		<li class="nav-item"><a class="nav-link"> <i
				class="fas far fa-check"></i> <span>${label_requirement6}</span></a></li>

		<li class="nav-item"><a class="nav-link"> <i
				class="fas far fa-check"></i> <span>${label_requirement7}</span></a></li>

		<li class="nav-item"><a class="nav-link"> <i
				class="fas far fa-check"></i> <span>${label_requirement8}</span></a></li>



	</ul>

	<!-- Divider -->
	<hr class="sidebar-divider d-none d-md-block">

	<div class="sidebar-heading">${label_advanced}</div>

	<ul class="navbar-nav ">
		<!-- Nav Item - Form -->
		<li class="nav-item"><a class="nav-link"> <i
				class="fas fa-check "></i> <span>${label_advanced1}</span></a></li>


	</ul>

	<!-- Divider -->
	<hr class="sidebar-divider d-none d-md-block">

</div>