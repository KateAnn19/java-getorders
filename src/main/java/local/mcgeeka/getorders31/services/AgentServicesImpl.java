package local.mcgeeka.getorders31.services;

import local.mcgeeka.getorders31.models.Agent;
import local.mcgeeka.getorders31.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
