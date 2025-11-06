package org.example.lab4um.controller;

import org.example.lab4um.entity.ApplicationRequest;
import org.example.lab4um.entity.Operators;
import org.example.lab4um.service.ApplicationRequestService;
import org.example.lab4um.service.CoursesService;
import org.example.lab4um.service.OperatorsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class ApplicationRequestController {

    private final ApplicationRequestService service;
    private final CoursesService coursesService;
    private final OperatorsService operatorsService;

    public ApplicationRequestController(ApplicationRequestService service,
                                        CoursesService coursesService,
                                        OperatorsService operatorsService) {
        this.service = service;
        this.coursesService = coursesService;
        this.operatorsService = operatorsService;
    }


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("requests", service.getAll());
        model.addAttribute("page", "index");
        return "index";
    }


    @GetMapping("/pending")
    public String pending(Model model) {
        model.addAttribute("requests", service.getPending());
        return "pending";
    }


    @GetMapping("/processed")
    public String processed(Model model) {
        model.addAttribute("requests", service.getProcessed());
        return "processed";
    }


    @GetMapping("/add")
    public String addForm(Model model) {
        ApplicationRequest req = new ApplicationRequest();
        // На всякий случай гарантируем дефолт
        req.setHandled(false);

        model.addAttribute("request", req);
        model.addAttribute("courses", coursesService.getAll());
        return "add";
    }


    @PostMapping("/add")
    public String addRequest(@ModelAttribute("request") ApplicationRequest request) {
        request.setHandled(false);
        service.save(request);
        return "redirect:/";
    }


    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id,
                          @RequestParam(name = "assign", required = false) Boolean assign,
                          Model model) {
        ApplicationRequest req = service.getById(id);
        if (req == null) {
            return "redirect:/";
        }

        model.addAttribute("request", req);
        model.addAttribute("operators", operatorsService.getAll()); // ✅ добавлено всегда
        model.addAttribute("openAssignModal", Boolean.TRUE.equals(assign));

        return "details";
    }


    @GetMapping("/process/{id}")
    public String process(@PathVariable Long id) {
        return "redirect:/details/" + id + "?assign=1";
    }


    @PostMapping("/assign/{id}")
    public String assign(@PathVariable Long id,
                         @RequestParam(name = "operatorIds", required = false) List<Long> operatorIds) {
        ApplicationRequest request = service.getById(id);
        if (request == null) {
            return "redirect:/";
        }


        if (request.isHandled() && request.getOperators() != null && !request.getOperators().isEmpty()) {
            return "redirect:/details/" + id;
        }

        Set<Operators> selected = new HashSet<>();
        if (operatorIds != null && !operatorIds.isEmpty()) {
            selected.addAll(operatorsService.getByIds(operatorIds));
        }

        request.setOperators(selected);
        request.setHandled(true);
        service.save(request);

        return "redirect:/details/" + id;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ApplicationRequest req = service.getById(id);
        if (req == null) {
            return "redirect:/";
        }
        model.addAttribute("request", req);
        model.addAttribute("courses", coursesService.getAll());
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute("request") ApplicationRequest form) {
        ApplicationRequest req = service.getById(id);
        if (req == null) {
            return "redirect:/";
        }


        req.setUserName(form.getUserName());
        req.setCommentary(form.getCommentary());
        req.setPhone(form.getPhone());
        req.setCourse(form.getCourse());
        service.save(req);

        return "redirect:/details/" + id;
    }


    @PostMapping("/details/{id}/operators/{opId}/remove")
    public String removeOperator(@PathVariable Long id, @PathVariable Long opId) {
        ApplicationRequest req = service.getById(id);
        if (req == null) {
            return "redirect:/";
        }

        if (req.getOperators() != null) {
            req.getOperators().removeIf(op -> op.getId().equals(opId));
            service.save(req);
        }
       return "redirect:/details/" + id;
    }
}
