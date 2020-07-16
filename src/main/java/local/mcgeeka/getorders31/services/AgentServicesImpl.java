package local.mcgeeka.getorders31.services;

import local.mcgeeka.getorders31.models.Agent;
import local.mcgeeka.getorders31.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service(value = "agentService")
public class AgentServicesImpl implements AgentService
{

    @Autowired
    private AgentsRepository agentrepos;

    @Override
    public Agent save(Agent agent)
    {
        return agentrepos.save(agent);
    }

    @Override
    public Agent findAgentByCode(long id){
        return agentrepos.findById(id).orElseThrow(() -> new EntityNotFoundException("Agent " + id + "Not Found"));
        //returns object instead of id
    }
}
