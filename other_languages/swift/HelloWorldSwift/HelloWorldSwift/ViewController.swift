//
//  ViewController.swift
//  HelloWorldSwift
//
//  Created by SHUTAO SHEN on 2/7/17.
//  Copyright © 2017 SHUTAO SHEN. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var nameLabel: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func hiNameAction(_ nameTextField: UITextField) {
        nameLabel.text = "Hi \(nameTextField.text!)"
    }
}

