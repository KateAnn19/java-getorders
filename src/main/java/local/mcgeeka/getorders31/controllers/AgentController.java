package local.mcgeeka.getorders31.controllers;

import local.mcgeeka.getorders31.models.Agent;
import local.mcgeeka.getorders31.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentController
{
    @Autowired
    private AgentService agentsService;

    //GET /agents/agent/{id} - Returns the agent and their customers with the given agent id
    @GetMapping(value = "/agent/{id}", produces = {"application/json"})
    public ResponseEntity<?> findAgentById(@PathVariable long id){
        Agent a = agentsService.findAgentByCode(id);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    //Stretch
    //DELETE http://localhost:2019/agents/unassigned/8
    //DELETE /agents/unassigned/{agentcode} - Deletes an agent if they are not assigned to a customer

}
