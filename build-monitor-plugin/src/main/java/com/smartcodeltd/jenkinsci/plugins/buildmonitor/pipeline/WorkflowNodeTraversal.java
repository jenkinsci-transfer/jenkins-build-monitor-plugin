package com.smartcodeltd.jenkinsci.plugins.buildmonitor.pipeline;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Collection;
import org.jenkinsci.plugins.workflow.cps.nodes.StepStartNode;
import org.jenkinsci.plugins.workflow.graph.FlowNode;
import org.jenkinsci.plugins.workflow.support.steps.StageStep;

public class WorkflowNodeTraversal extends BreadthFirstNodeTraversal<FlowNode> {

    @Override
    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "Descriptor should never be null")
    protected boolean isStageStep(FlowNode node) {
        return node instanceof StepStartNode
                && ((StepStartNode) node).getDescriptor().isSubTypeOf(StageStep.class);
    }

    @Override
    protected String getDisplayName(FlowNode node) {
        return node.getDisplayName();
    }

    @Override
    protected Collection<FlowNode> getParents(@NonNull FlowNode node) {
        return node.getParents();
    }
}
