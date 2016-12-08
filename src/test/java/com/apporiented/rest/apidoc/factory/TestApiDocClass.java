package com.apporiented.rest.apidoc.factory;

import com.apporiented.rest.apidoc.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(produces = {"application/json"}, consumes = {"application/json"})
@ApiDoc(name="test", description = "test description", errorResponseClasses = {String.class})
public class TestApiDocClass {

    @RequestMapping(value="/test/{id}", method = RequestMethod.POST, produces = {"application/json"})
    @ApiMethodDoc(value = "test method", responseClasses = {TestDTO.class})
    @ApiErrorsDoc({
            @ApiErrorDoc(
                    code = 401,
                    description = "Insufficient user privileges.")
    })
    public ResponseEntity<TestDTO> handlerMethod(
            @RequestBody TestDTO body,
            @ApiParamDoc("path variable") @PathVariable("id") Long id,
            @ApiParamDoc("request variable") @RequestParam("param") String param) {
        TestDTO dto = new TestDTO();
        dto.setStringField(id + "-" + param + "-" + body.getStringField());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value="test/deprecated", method = RequestMethod.GET,

            headers = {"header"},
            params = {"param"}
    )
    @ApiMethodDoc(value = "deprecated method")
    @Deprecated
    public DeferredResult<TestDTO> deprecatedMethod() {
        return null;
    }

}
