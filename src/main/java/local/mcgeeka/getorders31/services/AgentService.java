package local.mcgeeka.getorders31.services;

import local.mcgeeka.getorders31.models.Agent;

public interface AgentService
{
    Agent save(Agent agent);
    Agent findAgentByCode(long id);
}
