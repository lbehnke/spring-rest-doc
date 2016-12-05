Spring REST Doc
===============

This is a simple runtime REST API documentation generator based on [JSONDoc 1.0.1](https://github.com/fabiomaffioletti/jsondoc/releases/tag/v1.0.1). 
It reads [Spring MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html), 
[JAXB](https://jaxb.java.net/) annotations, and a set of own annotations. 

Example data transfer object returned by the REST controller:

    @ApiModelDoc("Data transfer object that represents an user account.")
    @XmlType(namespace = Namespaces.NS_DTO)
    @XmlRootElement(name="siteUser")
    public class AppUserDTO {
    
        @XmlAttribute(required = true)
        private String loginName;
        
        private String name;
        
        @XmlElement(required = true)
        private String email;
    
        [...]
        
    }
    
Example REST Controller:

    @ApiDoc(name="User resource", description="REST resource for administrating user accounts. Only acessible for users with administrator privilege.")
    @RestController
    @RequestMapping(value="/users")
    public class UserResource {
    
        @ApiMethodDoc("Returns a list of user accounts. Operation is available for administrators only.")
        @ApiErrorsDoc({
                @ApiErrorDoc(
                        code = 401,
                        description="Insufficient user privileges to access this operation."),
        })
    	@RequestMapping(method=RequestMethod.GET)
    	public AppUserListDTO findUsers(
                @RequestParam(value = "filter", required = false) @ApiParamDoc("Optional parameter that limits the result list to users with names containing the filter term as substring.") String filter,
                @RequestParam(value = "pageno", required = false) @ApiParamDoc("Page to be returned") Integer pageNo,
                @RequestParam(value = "pagesize", required = false) @ApiParamDoc("Page size") Integer pageSize,
                HttpServletRequest request) throws Exception
        {
            return userService.findAppUsers(filter, new PagingInfoDTO(pageNo, pageSize));
    	}
    	
    	[...]
    	
    }


Here is how the documentation is returned as XML or JSON object:

    private static  String[] PKG_NAMES = new String[]{
            "example.web.rest",
            "example.common.dto",
    };

    private DocumentationFactory documentationFactory = new DefaultDocumentationFactory();

    @ApiMethodDoc("Returns the API documentation as JSON or XML document.")
    @RequestMapping(value="/doc",method=RequestMethod.GET)
    public Documentation getDocumentation() {
        return documentationFactory.createDocumentation("1.0", "http://www.mydomain.org/api", PKG_NAMES);
    }

