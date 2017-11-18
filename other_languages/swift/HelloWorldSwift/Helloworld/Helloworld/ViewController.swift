//
//  ViewController.swift
//  Helloworld
//
//  Created by SHUTAO SHEN on 3/7/17.
//  Copyright © 2017 SHUTAO SHEN. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet var nameLabel: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func showNameAction(_ sender: UITextField) {
        nameLabel.text = "Hi \(sender.text!)"
    }

}

