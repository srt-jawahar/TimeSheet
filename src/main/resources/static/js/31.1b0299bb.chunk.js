(this["webpackJsonp@minimal/minimal-kit-react"]=this["webpackJsonp@minimal/minimal-kit-react"]||[]).push([[31],{781:function(e,t,a){"use strict";a.d(t,"a",(function(){return u}));var r=a(2),c=a(36),n=a(206),i=a(312),o=a(257),s=a(752),l=a(34),d=a(0),b=["links","action","heading","moreLink","sx"];function u(e){var t=e.links,a=e.action,u=e.heading,j=e.moreLink,h=void 0===j?[]:j,m=e.sx,p=Object(c.a)(e,b);return Object(d.jsxs)(i.a,{sx:Object(r.a)({mb:5},m),children:[Object(d.jsxs)(i.a,{sx:{display:"flex",alignItems:"center"},children:[Object(d.jsxs)(i.a,{sx:{flexGrow:1},children:[Object(d.jsx)(o.a,{variant:"h4",gutterBottom:!0,children:u}),Object(d.jsx)(l.a,Object(r.a)({links:t},p))]}),a&&Object(d.jsx)(i.a,{sx:{flexShrink:0},children:a})]}),Object(d.jsx)(i.a,{sx:{mt:2},children:Object(n.isString)(h)?Object(d.jsx)(s.a,{href:h,target:"_blank",variant:"body2",children:h}):h.map((function(e){return Object(d.jsx)(s.a,{noWrap:!0,href:e,variant:"body2",target:"_blank",sx:{display:"table"},children:e},e)}))})]})}},786:function(e,t,a){"use strict";var r=a(3),c=a(6),n=a(1),i=(a(10),a(11)),o=a(151),s=a(7),l=a(13),d=a(101),b=a(112);function u(e){return Object(d.a)("MuiCardContent",e)}Object(b.a)("MuiCardContent",["root"]);var j=a(0),h=["className","component"],m=Object(s.a)("div",{name:"MuiCardContent",slot:"Root",overridesResolver:function(e,t){return t.root}})((function(){return{padding:16,"&:last-child":{paddingBottom:24}}})),p=n.forwardRef((function(e,t){var a=Object(l.a)({props:e,name:"MuiCardContent"}),n=a.className,s=a.component,d=void 0===s?"div":s,b=Object(c.a)(a,h),p=Object(r.a)({},a,{component:d}),O=function(e){var t=e.classes;return Object(o.a)({root:["root"]},u,t)}(p);return Object(j.jsx)(m,Object(r.a)({as:d,className:Object(i.a)(O.root,n),ownerState:p,ref:t},b))}));t.a=p},801:function(e,t,a){"use strict";a.d(t,"b",(function(){return n}));var r=a(101),c=a(112);function n(e){return Object(r.a)("MuiSwitch",e)}var i=Object(c.a)("MuiSwitch",["root","edgeStart","edgeEnd","switchBase","colorPrimary","colorSecondary","sizeSmall","sizeMedium","checked","disabled","input","thumb","track"]);t.a=i},830:function(e,t,a){"use strict";var r=a(4),c=a(6),n=a(3),i=a(1),o=(a(10),a(11)),s=a(151),l=a(64),d=a(15),b=a(318),u=a(13),j=a(7),h=a(801),m=a(0),p=["className","color","edge","size","sx"],O=Object(j.a)("span",{name:"MuiSwitch",slot:"Root",overridesResolver:function(e,t){var a=e.ownerState;return[t.root,a.edge&&t["edge".concat(Object(d.a)(a.edge))],t["size".concat(Object(d.a)(a.size))]]}})((function(e){var t,a=e.ownerState;return Object(n.a)({display:"inline-flex",width:58,height:38,overflow:"hidden",padding:12,boxSizing:"border-box",position:"relative",flexShrink:0,zIndex:0,verticalAlign:"middle","@media print":{colorAdjust:"exact"}},"start"===a.edge&&{marginLeft:-8},"end"===a.edge&&{marginRight:-8},"small"===a.size&&(t={width:40,height:24,padding:7},Object(r.a)(t,"& .".concat(h.a.thumb),{width:16,height:16}),Object(r.a)(t,"& .".concat(h.a.switchBase),Object(r.a)({padding:4},"&.".concat(h.a.checked),{transform:"translateX(16px)"})),t))})),g=Object(j.a)(b.a,{name:"MuiSwitch",slot:"SwitchBase",overridesResolver:function(e,t){var a=e.ownerState;return[t.switchBase,Object(r.a)({},"& .".concat(h.a.input),t.input),"default"!==a.color&&t["color".concat(Object(d.a)(a.color))]]}})((function(e){var t,a=e.theme;return t={position:"absolute",top:0,left:0,zIndex:1,color:"light"===a.palette.mode?a.palette.common.white:a.palette.grey[300],transition:a.transitions.create(["left","transform"],{duration:a.transitions.duration.shortest})},Object(r.a)(t,"&.".concat(h.a.checked),{transform:"translateX(20px)"}),Object(r.a)(t,"&.".concat(h.a.disabled),{color:"light"===a.palette.mode?a.palette.grey[100]:a.palette.grey[600]}),Object(r.a)(t,"&.".concat(h.a.checked," + .").concat(h.a.track),{opacity:.5}),Object(r.a)(t,"&.".concat(h.a.disabled," + .").concat(h.a.track),{opacity:"light"===a.palette.mode?.12:.2}),Object(r.a)(t,"& .".concat(h.a.input),{left:"-100%",width:"300%"}),t}),(function(e){var t,a=e.theme,c=e.ownerState;return Object(n.a)({"&:hover":{backgroundColor:Object(l.a)(a.palette.action.active,a.palette.action.hoverOpacity),"@media (hover: none)":{backgroundColor:"transparent"}}},"default"!==c.color&&(t={},Object(r.a)(t,"&.".concat(h.a.checked),Object(r.a)({color:a.palette[c.color].main,"&:hover":{backgroundColor:Object(l.a)(a.palette[c.color].main,a.palette.action.hoverOpacity),"@media (hover: none)":{backgroundColor:"transparent"}}},"&.".concat(h.a.disabled),{color:"light"===a.palette.mode?Object(l.e)(a.palette[c.color].main,.62):Object(l.b)(a.palette[c.color].main,.55)})),Object(r.a)(t,"&.".concat(h.a.checked," + .").concat(h.a.track),{backgroundColor:a.palette[c.color].main}),t))})),x=Object(j.a)("span",{name:"MuiSwitch",slot:"Track",overridesResolver:function(e,t){return t.track}})((function(e){var t=e.theme;return{height:"100%",width:"100%",borderRadius:7,zIndex:-1,transition:t.transitions.create(["opacity","background-color"],{duration:t.transitions.duration.shortest}),backgroundColor:"light"===t.palette.mode?t.palette.common.black:t.palette.common.white,opacity:"light"===t.palette.mode?.38:.3}})),f=Object(j.a)("span",{name:"MuiSwitch",slot:"Thumb",overridesResolver:function(e,t){return t.thumb}})((function(e){return{boxShadow:e.theme.shadows[1],backgroundColor:"currentColor",width:20,height:20,borderRadius:"50%"}})),v=i.forwardRef((function(e,t){var a=Object(u.a)({props:e,name:"MuiSwitch"}),r=a.className,i=a.color,l=void 0===i?"primary":i,b=a.edge,j=void 0!==b&&b,v=a.size,k=void 0===v?"medium":v,w=a.sx,_=Object(c.a)(a,p),S=Object(n.a)({},a,{color:l,edge:j,size:k}),y=function(e){var t=e.classes,a=e.edge,r=e.size,c=e.color,i=e.checked,o=e.disabled,l={root:["root",a&&"edge".concat(Object(d.a)(a)),"size".concat(Object(d.a)(r))],switchBase:["switchBase","color".concat(Object(d.a)(c)),i&&"checked",o&&"disabled"],thumb:["thumb"],track:["track"],input:["input"]},b=Object(s.a)(l,h.b,t);return Object(n.a)({},t,b)}(S),C=Object(m.jsx)(f,{className:y.thumb,ownerState:S});return Object(m.jsxs)(O,{className:Object(o.a)(y.root,r),sx:w,ownerState:S,children:[Object(m.jsx)(g,Object(n.a)({type:"checkbox",icon:C,checkedIcon:C,ref:t,ownerState:S},_,{classes:Object(n.a)({},y,{root:y.switchBase})})),Object(m.jsx)(x,{className:y.track,ownerState:S})]})}));t.a=v},914:function(e,t,a){"use strict";a.r(t),a.d(t,"default",(function(){return z}));var r=a(2),c=a(8),n=a.n(c),i=a(18),o=(a(1),a(764)),s=a(748),l=a(724),d=a(765),b=a(773),u=a(786),j=a(47),h=a(76),m=a(58),p=a(27),O=a(780),g=a(772),x=a(830),f=a(744),v=a(775),k=a(769),w=a(767),_=a(741),S=a(31),y=a(781),C=a(152),R=a(113),M=a(0);function z(){var e=Object(C.a)().themeStretch,t="Timesheet User Configuration Update",a=Object(h.b)().enqueueSnackbar,c=Object(p.i)().employeeId;console.log("\ud83d\ude80 => employeeId",c);var z=j.c().shape({reporting_manager_emp_id:j.e().required("Reporting manager is required"),kpi_kra_group_id:j.b().required("KPI-KRA Group is required")}),I=Object(m.d)({initialValues:{reporting_manager_emp_id:"",kpi_kra_group_id:""},validationSchema:z,onSubmit:function(){var e=Object(i.a)(n.a.mark((function e(t,r){var c;return n.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:c=r.setSubmitting;try{console.log("\ud83d\ude80 => values",t),c(!1),a("Updated successfully",{variant:"success"})}catch(n){console.error(n),c(!1)}case 2:case"end":return e.stop()}}),e)})));return function(t,a){return e.apply(this,arguments)}}()}),N=I.errors,B=I.touched,T=I.handleSubmit,W=I.isSubmitting,U=I.getFieldProps;return Object(M.jsx)(R.a,{title:t,children:Object(M.jsxs)(o.a,{maxWidth:!e&&"lg",children:[Object(M.jsx)(y.a,{heading:t,links:[{name:"Dashboard",href:S.b.general.root},{name:"Settings",href:S.b.timesheet.settings},{name:"Timesheet User Configurations",href:S.b.timesheet.timesheetUserConfigurations},{name:"Update"}]}),Object(M.jsx)(b.a,{children:Object(M.jsx)(u.a,{children:Object(M.jsx)(m.c,{value:I,children:Object(M.jsx)(m.b,{noValidate:!0,autoComplete:"off",onSubmit:T,children:Object(M.jsx)(s.a,{container:!0,spacing:3,children:Object(M.jsx)(s.a,{item:!0,xs:12,md:12,children:Object(M.jsxs)(l.a,{spacing:3,children:[Object(M.jsxs)(l.a,{direction:{xs:"column",sm:"row"},spacing:{xs:3,sm:2},children:[Object(M.jsx)(d.a,{fullWidth:!0,label:"Username",defaultValue:"Monish",disabled:!0}),Object(M.jsx)(d.a,{fullWidth:!0,label:"Name",defaultValue:"Monish",disabled:!0}),Object(M.jsx)(d.a,{fullWidth:!0,label:"Employee Id",defaultValue:"M1130",disabled:!0})]}),Object(M.jsxs)(l.a,{direction:{xs:"column",sm:"row"},spacing:{xs:3,sm:2},children:[Object(M.jsx)(d.a,{fullWidth:!0,label:"Email Id",defaultValue:"monish.n@focusrtech.com",disabled:!0}),Object(M.jsx)(d.a,{fullWidth:!0,label:"Role",defaultValue:"Team member",disabled:!0}),Object(M.jsx)(g.a,{control:Object(M.jsx)(x.a,{defaultChecked:!0}),label:"Status",disabled:!0})]}),Object(M.jsxs)(l.a,{direction:{xs:"column",sm:"row"},spacing:{xs:3,sm:2},children:[Object(M.jsxs)(w.a,{fullWidth:!0,children:[Object(M.jsx)(f.a,{id:"reporting-manager",children:"Reporting Manager"}),Object(M.jsxs)(_.a,Object(r.a)(Object(r.a)({},U("reporting_manager_emp_id")),{},{labelId:"reporting-manager",id:"reporting-manager-id",label:"Reporting Manager",error:Boolean(B.reporting_manager_emp_id&&N.reporting_manager_emp_id),children:[Object(M.jsx)(v.a,{value:"",children:Object(M.jsx)("em",{children:"None"})}),Object(M.jsx)(v.a,{value:10,children:"Ten"}),Object(M.jsx)(v.a,{value:20,children:"Twenty"}),Object(M.jsx)(v.a,{value:30,children:"Thirty"})]})),Object(M.jsx)(k.a,{children:B.reporting_manager_emp_id&&N.reporting_manager_emp_id})]}),Object(M.jsxs)(w.a,{fullWidth:!0,children:[Object(M.jsx)(f.a,{id:"kpi-kra-group-name",children:"KPI-KRA Group Name"}),Object(M.jsxs)(_.a,Object(r.a)(Object(r.a)({},U("kpi_kra_group_id")),{},{labelId:"kpi-kra-group-name",id:"kpi-kra-group-name-id",label:"KPI-KRA Group Name",error:Boolean(B.kpi_kra_group_id&&N.kpi_kra_group_id),children:[Object(M.jsx)(v.a,{value:"",children:Object(M.jsx)("em",{children:"None"})}),Object(M.jsx)(v.a,{value:10,children:"Ten"}),Object(M.jsx)(v.a,{value:20,children:"Twenty"}),Object(M.jsx)(v.a,{value:30,children:"Thirty"})]})),Object(M.jsx)(k.a,{children:B.kpi_kra_group_id&&N.kpi_kra_group_id})]})]}),Object(M.jsx)(l.a,{direction:{xs:"column",sm:"row"},spacing:{xs:3,sm:2},children:Object(M.jsx)(O.a,{fullWidth:!0,type:"submit",variant:"contained",size:"large",loading:W,children:"Save"})})]})})})})})})})]})})}}}]);
//# sourceMappingURL=31.1b0299bb.chunk.js.map