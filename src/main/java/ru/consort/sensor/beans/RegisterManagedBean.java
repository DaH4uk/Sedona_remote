package ru.consort.sensor.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import ru.consort.sensor.Services.RegisterService;
import ru.consort.sensor.Services.RegisterUpdateService;


/**
 * Created by DaH4uk on 06.07.2016.
 * Realised registers.xhtml functionality.
 * https://konsort.planfix.ru/task/32615
 */


@ManagedBean(name = "registerManagedBean")
@ViewScoped
public class RegisterManagedBean implements Serializable {
    private TreeNode root;
    private TreeNode selectedNode;
    private Map<TreeNode, String> treeNodeStringMap = new HashMap<>();
    private String url;

    //JSF view initialization
    @PostConstruct
    public void init() {
        root = new DefaultTreeNode("Points", null);

        TreeNode numericNode = new DefaultTreeNode("Analog Input", root);
        TreeNode numeri1Node = new DefaultTreeNode("Analog Output", root);
        TreeNode discretNode = new DefaultTreeNode("Analog Input 1", root);
        root.setSelectable(false);
//        numeri1Node.setSelectable(false);
        numericNode.setSelectable(false);
        discretNode.setSelectable(false);
        //запуск обновления информации о регистрах
        RegisterUpdateService.getInstance();

        //RegistersMap parsing
        for (String s : RegisterService.getRegistersMap().keySet()) {
            System.out.println(s.substring(0, 3));
            if (s.substring(0, 2).equals("AI")) {

                TreeNode node = new DefaultTreeNode(RegisterService.getRegistersMap().get(s).getDescription(), numericNode);
                treeNodeStringMap.put(node, s);

            }
            if (s.substring(0, 2).equals("AO")) {
                TreeNode node = new DefaultTreeNode(RegisterService.getRegistersMap().get(s).getDescription(), numeri1Node);
                treeNodeStringMap.put(node, s);

            }
            if (s.substring(0, 3).equals("AI1")) {
                TreeNode node = new DefaultTreeNode(RegisterService.getRegistersMap().get(s).getDescription(), discretNode);
                treeNodeStringMap.put(node, s);
            }
        }


    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }


    public void setRoot(TreeNode root) {
        this.root = root;
    }

    //implements the functionality of the select button
    public void displaySelectedSingle() {
        if (selectedNode != null) {
            //show message
            FacesMessage message = new FacesMessage("Выбран модуль: ", selectedNode.getData().toString() + " Url: " + treeNodeStringMap.get(selectedNode));
            FacesContext.getCurrentInstance().addMessage(null, message);

            //set options
            Map<String, Object> options = new HashMap<>();
            options.put("modal", true);
            options.put("width", 700);
            options.put("height", 390);
            options.put("contentWidth", "100%");
            options.put("contentHeight", "100%");
            options.put("headerElement", "customheader");

            //send a url of the selected treenode in session map
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("url",treeNodeStringMap.get(selectedNode));

            RequestContext.getCurrentInstance().openDialog("registerInfo", options, null);


        }
    }


    public void cancel() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }


    public TreeNode getRoot() {
        return root;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
